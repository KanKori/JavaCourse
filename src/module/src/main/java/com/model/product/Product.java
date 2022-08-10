package com.model.product;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public abstract class Product {
    protected final String id;
    protected final String series;
    protected double price;
    protected final ProductType type;

    protected Product(String series, double price, ProductType type) {
        this.id = UUID.randomUUID().toString();
        this.series = series;
        this.price = price;
        this.type = type;
    }
}
