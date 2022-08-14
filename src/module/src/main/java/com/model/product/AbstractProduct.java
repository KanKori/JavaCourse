package com.model.product;

import com.model.product.specifications.ProductType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public abstract class AbstractProduct {
    protected String id;
    protected String series;
    protected double price;
    protected final ProductType type;

    protected AbstractProduct(String series, double price, ProductType type) {
        this.id = UUID.randomUUID().toString();
        this.series = series;
        this.price = price;
        this.type = type;
    }
}
