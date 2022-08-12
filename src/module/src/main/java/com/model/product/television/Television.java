package com.model.product.television;

import com.model.product.AbstractProduct;
import com.model.product.specifications.ProductType;
import com.model.product.television.specifications.TelevisionScreenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Television extends AbstractProduct {
    private final double diagonal;
    private final String country;
    private final TelevisionScreenType screenType;

    public Television(String series, double price,
                      double diagonal, String country,
                      TelevisionScreenType screenType) {
        super(series, price, ProductType.TELEVISION);
        this.diagonal = diagonal;
        this.country = country;
        this.screenType = screenType;
    }

    @Override
    public String toString() {
        return "Television {" + "\n" +
                "Series " + series + "\n" +
                "Diagonal " + diagonal + "\n" +
                "Country " + country + "\n" +
                "ScreenType " + screenType + "\n" +
                "Price " + price + "\n" +
                "}";
    }
}
