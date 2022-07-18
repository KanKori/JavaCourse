package com.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Laptop extends Product {
    private final String model;
    private final LaptopManufacturer laptopManufacturer;

    public Laptop(String title, int count, double price, String model, LaptopManufacturer laptopManufacturer) {
        super(title, count, price);
        this.model = model;
        this.laptopManufacturer = laptopManufacturer;
    }

    @Override
    public String toString() {
        return "Laptop{" + "manufacturer = " + laptopManufacturer +
                ", id = " + id + '\'' +
                ", title='" + title + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
