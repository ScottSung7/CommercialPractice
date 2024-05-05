package com.example.orderapi.application.application;


import com.example.orderapi.application.service.CartService;
import com.example.orderapi.application.service.ProductSearchService;
import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.domain.model.redis.Cart;
import com.example.orderapi.web.validation.exception.OrderErrorCode;
import com.example.orderapi.web.validation.exception.OrderException;
import com.example.orderapi.web.validation.form.cart.AddProductCartForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.orderapi.web.validation.exception.OrderErrorCode.ITEM_COUNT_NOT_ENOUGH;
import static com.example.orderapi.web.validation.exception.OrderErrorCode.NOT_FOUND_PRODUCT;

@Service
@RequiredArgsConstructor
public class CartApplication {

    private final ProductSearchService productSearchService;
    private final CartService cartService;

    public Cart addCart(Long customerId, AddProductCartForm form){
        Product product = productSearchService.getByProductId(form.getId());
        if(product==null){
            throw new OrderException(NOT_FOUND_PRODUCT);
        }
        Cart cart = cartService.getCart(customerId);
        if(!addAble(cart,product,form)){
            throw new OrderException(ITEM_COUNT_NOT_ENOUGH);
        }
        return cartService.addCart(customerId,form);
    }
        //private methods - addCart
        private boolean addAble(Cart cart,Product product,AddProductCartForm form) {
            Product cartProduct = cart.getProducts().stream().filter(p -> p.getId().equals(form.getId()))
                    .findFirst().orElse(Product.builder().id(product.getId())
                            .productItems(Collections.emptyList()).build());
            return checkAvailabilityOfProductItems(cartProduct, product, form);
        }
        private boolean checkAvailabilityOfProductItems(Product cartProduct, Product product, AddProductCartForm form) {
            Map<Long,Integer> cartItemCountMap = cartProduct.getProductItems().stream()
                    .collect(Collectors.toMap(ProductItem::getId,ProductItem::getCount));
            Map<Long,Integer> currentItemCountMap = product.getProductItems().stream()
                    .collect(Collectors.toMap(ProductItem::getId,ProductItem::getCount));
            return form.getAddCartProductItemForms().stream().noneMatch(
                    formItem -> {
                        Integer cartCount = cartItemCountMap.get(formItem.getProductId());
                        if(cartCount == null){
                            cartCount = 0;
                        }
                        Integer currentCount = currentItemCountMap.get(formItem.getProductId());
                        return formItem.getCount() + cartCount > currentCount;
                    });
        }
    public Cart getCart(Long customerId){
        Cart newcart = cartService.getCart(customerId);
        if(newcart.checkEmptyCart()){return newcart;}
        Cart cart = refreshCart(newcart);
        Cart cartToReturn = updateRefreshedCart(customerId, cart);
        flushCartMessages(customerId, cart);
        return cartToReturn;
    }
        //private methods - getCart
        private Cart updateRefreshedCart(Long customerId, Cart cart) {
            cartService.putCart(cart.getCustomerId(), cart);
            Cart cartToReturn = Cart.getEmptyCart(customerId);
            cartToReturn.collectProducts(cart.getProducts());
            cartToReturn.collectMessages(cart.getMessages());
            return cartToReturn;
        }
        private void flushCartMessages(Long customerId, Cart cart) {
            cart.collectMessages(new ArrayList<>());
            cartService.putCart(customerId, cart);
        }
    public void clearCart(Long customerId){
        cartService.putCart(customerId,null);
    }
    protected Cart refreshCart(Cart cart){
        Map<Long,Product> productMap = productSearchService.getListByProductIds(
                        cart.getProducts().stream().map(Product::getId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        cart = manageProductsAndProductItems(cart, productMap);
        return cart;
    }
        //private methods - refreshCart
        private Cart manageProductsAndProductItems(Cart cart, Map<Long, Product> productMap){
            for(int i= 0; i < cart.getProducts().size(); i++){
                Product cartProduct = cart.getProducts().get(i);
                Product p = productMap.get(cartProduct.getId());
                System.out.println(p.getProductItems().size());
                if(productNotExist(cart, cartProduct, p)){
                    i--;
                    continue;
                }
                List<String> tmpMessages = collectIssuesFromProductItems(cart, cartProduct, p);
                i = noOptionExistForProduct(cart, i, cartProduct);
                cart = sumUpCartMessages(cart, cartProduct, tmpMessages);
            }
            return cart;
        }
        private boolean productNotExist(Cart cart, Product cartProduct, Product p) {
            if(p == null){
                cart.getProducts().remove(cartProduct);
                cart.addMessage(cartProduct.getName()+" 상품이 삭제되었습니다.");
                return true;
            }
            return false;
        }
        private int noOptionExistForProduct(Cart cart, int i, Product cartProduct) {
            if(cartProduct.getProductItems().size() == 0){
                cart.getProducts().remove(cartProduct);
                i--;
                cart.addMessage(cartProduct.getName()+" 상품의 옵션이 모두 없어져 구매가 불가능합니다.");
            }
            return i;
        }
        private Cart sumUpCartMessages(Cart cart, Product cartProduct, List<String> tmpMessages) {
            if(tmpMessages.size() > 0){
                StringBuilder builder = new StringBuilder();
                builder.append(cartProduct.getName()+" 상품의 변동 사항 : ");
                for(String message : tmpMessages){
                    builder.append(message);
                }
                cart.addMessage(builder.toString());
            }
            return cart;
        }
        private List<String> collectIssuesFromProductItems(Cart cart, Product cartProduct, Product p) {
            Map<Long,ProductItem> productItemMap = p.getProductItems().stream()
                    .collect(Collectors.toMap(ProductItem::getId, productItem -> productItem));
            List<String> tmpMessages = new ArrayList<>();

            for(int j=0; j < cartProduct.getProductItems().size();j++){

                ProductItem cartProductItem = cartProduct.getProductItems().get(j);
                ProductItem pi = productItemMap.get(cartProductItem.getId());
                if(pi == null){
                    cartProduct.getProductItems().remove(cartProductItem);
                    j--;
                    tmpMessages.add(cartProductItem.getName()+" 옵션이 삭제되었습니다.");
                    continue;
                }

                boolean isPriceChanged = false, isCountNotEnough=false;
                //가격 반영
                if(!cartProductItem.getPrice().equals(pi.getPrice())){
                    isPriceChanged =true;
                    cartProductItem.changePrice(pi.getPrice());
                }
                //수량 반영
                if(cartProductItem.getCount() > pi.getCount()) {
                    isCountNotEnough = true;
                    cartProductItem.changeCount(pi.getCount());
                }
                //가격, 수량 변동시
                if(isPriceChanged && isCountNotEnough){
                    tmpMessages.add(cartProductItem.getName()+" 가격변동, 수량이 부족하여 구매 가능한 최대치로 변경되었습니다.");
                }else if(isPriceChanged){
                    tmpMessages.add(cartProductItem.getName()+" 가격이 변동되었습니다.");
                }else if(isCountNotEnough){
                    tmpMessages.add(cartProductItem.getName()+" 수량이 부족하여 구매 가능한 최대치로 변경되었습니다.");
                }
            }
            return tmpMessages;
        }



}
