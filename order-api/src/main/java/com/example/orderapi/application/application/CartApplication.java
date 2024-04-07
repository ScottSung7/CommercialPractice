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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    public Cart updateCart(Long customerId, Cart cart){
        // 실질적으로 변하는 데이터
        // 상품의 삭제, 수량 변경
        cartService.putCart(customerId,cart);
        return getCart(customerId);
    }

    //1. 장바구니에 상품을 추가 했다.
    //2. 상품의 가격이나 수량이 변동 된다.
    public Cart getCart(Long customerId){

        Cart newcart = cartService.getCart(customerId);
        System.out.println(newcart);
        Cart cart = refreshCart(newcart);
        cartService.putCart(cart.getCustomerId(),cart);
        Cart returnCart = new Cart();
        returnCart.setCustomerId(customerId);
        returnCart.setProducts(cart.getProducts());
        returnCart.setMessages(cart.getMessages());
        cart.setMessages(new ArrayList<>());
        // 메세지 없는 것
        cartService.putCart(customerId,cart);
        return returnCart;

        // 2. 메세지를 보고 난 다음에는, 이미 본 메세지는 스팸이 되기 때문에 제거한다.
    }

    public void clearCart(Long customerId){
        cartService.putCart(customerId,null);
    }

    protected Cart refreshCart(Cart cart){
        // 1. 상품이나 상품의 아이템의 정보, 가격, 수량이 변경되었는지 체크하고
        // 그에 맞는 알람을 제공해준다.
        // 2. 상품의 수량, 가격을 우리가 임의로 변경한다.


        Map<Long,Product> productMap = productSearchService.getListByProductIds(
                        cart.getProducts().stream().map(Product::getId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        for(int i= 0;i< cart.getProducts().size();i++){

            Product cartProduct = cart.getProducts().get(i);

            Product p = productMap.get(cartProduct.getId());
            if(p == null){
                cart.getProducts().remove(cartProduct);
                i--;
                cart.addMessage(cartProduct.getName()+" 상품이 삭제되었습니다.");
                continue;
            }

            Map<Long,ProductItem> productItemMap = p.getProductItems().stream()
                    .collect(Collectors.toMap(ProductItem::getId, productItem -> productItem));


            // 아이템 1,2
            List<String> tmpMessages = new ArrayList<>();
            for(int j=0;j<cartProduct.getProductItems().size();j++){
                ProductItem cartProductItem = cartProduct.getProductItems().get(j);
                ProductItem pi = productItemMap.get(cartProductItem.getId());
                if(pi == null){
                    cartProduct.getProductItems().remove(cartProductItem);
                    j--;
                    tmpMessages.add(cartProductItem.getName()+" 옵션이 삭제되었습니다.");
                    continue;
                }

                boolean isPriceChanged = false,isCountNotEnough=false;
                if(!cartProductItem.getPrice().equals(pi.getPrice())){
                    isPriceChanged =true;
                    cartProductItem.setPrice(pi.getPrice());
                }
                if(cartProductItem.getCount() > pi.getCount()) {
                    isCountNotEnough = true;
                    cartProductItem.setCount(pi.getCount());
                }
                if(isPriceChanged && isCountNotEnough){
                    tmpMessages.add(cartProductItem.getName()+" 가격변동, 수량이 부족하여 구매 가능한 최대치로 변경되었습니다.");
                }else if(isPriceChanged){
                    tmpMessages.add(cartProductItem.getName()+" 가격이 변동되었습니다.");
                }else if(isCountNotEnough){
                    tmpMessages.add(cartProductItem.getName()+" 수량이 부족하여 구매 가능한 최대치로 변경되었습니다.");
                }
            }
            //
            if(cartProduct.getProductItems().size() == 0){
                cart.getProducts().remove(cartProduct);
                i--;
                cart.addMessage(cartProduct.getName()+" 상품의 옵션이 모두 없어져 구매가 불가능합니다.");
            }
            else if(tmpMessages.size() > 0){
                StringBuilder builder = new StringBuilder();
                builder.append(cartProduct.getName()+" 상품의 변동 사항 : ");
                for(String message : tmpMessages){
                    builder.append(message);
                    builder.append(", ");
                }
                cart.addMessage(builder.toString());
            }
        }
        return cart;
    }

    private boolean addAble(Cart cart,Product product,AddProductCartForm form){
        Product cartProduct =  cart.getProducts().stream().filter(p -> p.getId().equals(form.getId()))
                .findFirst().orElse(Product.builder().id(product.getId())
                        .productItems(Collections.emptyList()).build());

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

}
