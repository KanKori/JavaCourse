package com.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Laptop extends Product {
    private final String model;
    private final LaptopManufacturer laptopManufacturer;

    public Laptop(String title, int count, double price, String model, LaptopManufacturer laptopManufacturer) {
        super(title, count, price, ProductType.LAPTOP);
        this.model = model;
        this.laptopManufacturer = laptopManufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Laptop laptop = (Laptop) o;
        return Objects.equals(id, laptop.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
