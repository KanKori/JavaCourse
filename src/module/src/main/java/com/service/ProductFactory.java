package com.service;

import com.model.product.AbstractProduct;
import com.model.product.telephone.Telephone;
import com.model.product.telephone.specifications.TelephoneScreenType;
import com.model.product.television.Television;
import com.model.product.television.specifications.TelevisionScreenType;

import java.util.Map;
import java.util.function.Function;

public class ProductFactory {
    private static final String TYPE = "type";
    private static final String MODEL = "model";
    private static final String PRICE = "price";
    private static final String SCREEN_TYPE = "screen type";
    private static final String SERIES = "series";
    private static final String COUNTRY = "country";
    private static final String DIAGONAL = "diagonal";

    private ProductFactory() {
    }

    public static AbstractProduct createProductFromMap(Map<String, String> fields) {
        Function<Map<String, String>, AbstractProduct> mapToProduct = map -> {
            switch (map.get(TYPE)) {
                case "Telephone" -> {
                    return new Telephone(
                            map.get(SERIES),
                            map.get(MODEL),
                            Double.parseDouble(map.get(PRICE)),
                            TelephoneScreenType.valueOf(map.get(SCREEN_TYPE)));
                }
                case "Television" -> {
                    return new Television(
                            map.get(SERIES),
                            Double.parseDouble(map.get(PRICE)),
                            Double.parseDouble(map.get(DIAGONAL)),
                            map.get(COUNTRY),
                            TelevisionScreenType.valueOf(map.get(SCREEN_TYPE)));
                }
                default -> throw new IllegalArgumentException("oops, i cant create a " + map.get(TYPE));
            }
        };
        return mapToProduct.apply(fields);
    }
}
