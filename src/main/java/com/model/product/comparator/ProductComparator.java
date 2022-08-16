package com.model.product.comparator;

import com.model.product.Product;

import java.util.Comparator;

public class ProductComparator<T extends Product> implements Comparator<T> {
    @Override
    public int compare(T firstProduct, T secondProduct) {
        if (secondProduct.getPrice() == firstProduct.getPrice()) {
            if (firstProduct.getTitle().equals(secondProduct.getTitle())) {
                return Integer.compare(firstProduct.getCount(), secondProduct.getCount());
            }
            return firstProduct.getTitle().compareTo(secondProduct.getTitle());
        }
        return Double.compare(secondProduct.getPrice(), firstProduct.getPrice());
    }
}
