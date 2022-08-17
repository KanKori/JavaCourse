package com.example.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductBundle extends Product {
    protected int amount;

    @Override
    public String toString() {
        return "ProductBundle{" +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amountInBundle=" + amount +
                '}';
    }
}