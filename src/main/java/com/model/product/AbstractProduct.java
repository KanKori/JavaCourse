package com.model.product;

import com.model.product.specifications.ProductType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class AbstractProduct {
    protected String id;
    protected String title;
    protected int count;
    protected double price;
    protected final ProductType type;

    protected AbstractProduct(String title, int count, double price, ProductType type) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.count = count;
        this.price = price;
        this.type = type;
    }
}
