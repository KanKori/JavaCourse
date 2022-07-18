package com.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tablet extends Product {
    private  final String model;
    private final TabletManufacturer tabletManufacturer;

    public Tablet(String title, int count, double price, String model, TabletManufacturer tabletManufacturer) {
        super(title, count, price);
        this.model = model;
        this.tabletManufacturer = tabletManufacturer;
    }

    @Override
    public String toString() {
        return "Tablet{" + "manufacturer = " + tabletManufacturer +
                ", id = " + id + '\'' +
                ", title='" + title + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
