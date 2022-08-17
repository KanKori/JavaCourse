package com.service.product;

import com.model.product.AbstractProduct;

import java.util.Random;

public class ProductContainer<T extends AbstractProduct> {
    private static final Random RANDOM = new Random();
    private T product;

    public T getProduct() {
        return product;
    }

    public void setProduct(T product) {
        this.product = product;
    }

    public void applyRandomDiscount() {
        final int DISCOUNT_RATE_START = 1;
        final int LOWEST_DISCOUNT_RATE = DISCOUNT_RATE_START + 10;
        final int HIGHEST_DISCOUNT_RATE = DISCOUNT_RATE_START + 30;
        final int FULL_PRICE_RATE = 100;
        int discount = RANDOM.nextInt(LOWEST_DISCOUNT_RATE, HIGHEST_DISCOUNT_RATE);
        double priceWithDiscount = (product.getPrice() - (product.getPrice() * (discount / FULL_PRICE_RATE)));
        product.setPrice(priceWithDiscount);
    }

    public <X extends Number> void increaseCountOfProduct(X increaseCount) {
        product.setCount(product.getCount() + increaseCount.intValue());
    }
}
