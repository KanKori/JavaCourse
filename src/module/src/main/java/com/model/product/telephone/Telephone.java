package com.model.product.telephone;

import com.model.product.Product;
import com.model.product.ProductType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Telephone extends Product {
    private final String model;
    private final TelephoneScreenType screenType;

    protected Telephone(String series, String model, double price, ProductType type, TelephoneScreenType screenType) {
        super(series, price, ProductType.TELEPHONE);
        this.model = model;
        this.screenType = screenType;
    }
}
