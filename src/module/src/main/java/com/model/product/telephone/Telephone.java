package com.model.product.telephone;

import com.model.product.AbstractProduct;
import com.model.product.specifications.ProductType;
import com.model.product.telephone.specifications.TelephoneScreenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Telephone extends AbstractProduct {
    private final String model;
    private final TelephoneScreenType screenType;

    protected Telephone(String series, String model, double price, ProductType type, TelephoneScreenType screenType) {
        super(series, price, ProductType.TELEPHONE);
        this.model = model;
        this.screenType = screenType;
    }
}
