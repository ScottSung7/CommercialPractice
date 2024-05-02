package com.example.orderapi.repository;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductREpositoryImpl implements ProductRepositoryCustom{

    private JPAQueryFactory queryFactory;

    @Override
    public List<Product> searchByName(String name) {
        String search = "%" + name + "%";

        QProduct product = QProduct.product;
        return queryFactory.selectFrom(product)
                .where(product.name.like(search))
                .fetch();
    }
}
