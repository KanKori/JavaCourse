package com.model.product.telephone;

import com.model.product.AbstractProduct;
import com.model.product.specifications.ProductType;
import com.model.product.telephone.specifications.TelephoneScreenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Telephone extends AbstractProduct {
    private String model;
    private TelephoneScreenType screenType;

    public Telephone(String series, String model,
                     double price,
                     TelephoneScreenType screenType) {
        super(series, price, ProductType.TELEPHONE);
        this.model = model;
        this.screenType = screenType;
    }

    @Override
    public String toString() {
        return "Telephone {" + "\n" +
                "Series " + series + "\n" +
                "Model " + model + "\n" +
                "Screen Type " + screenType + "\n" +
                "Price " + price + "\n" +
                "}" + "\n";
    }
}
