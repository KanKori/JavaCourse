package com.model.product.television;

import com.model.product.Product;
import com.model.product.ProductType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Television extends Product {
    private final double diagonal;
    private final String country;
    private final TelevisionScreenType screenType;

    public Television(String series, double price, ProductType type, double diagonal, String country, TelevisionScreenType screenType) {
        super(series, price, type);
        this.diagonal = diagonal;
        this.country = country;
        this.screenType = screenType;
    }
}
