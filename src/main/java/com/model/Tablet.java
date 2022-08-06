package com.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Tablet extends Product {
    private final String model;
    private final TabletManufacturer tabletManufacturer;
    private List<String> details;

    public Tablet(String title, int count, double price, String model, TabletManufacturer tabletManufacturer, List<String> details) {
        super(title, count, price, ProductType.TABLET);
        this.model = model;
        this.tabletManufacturer = tabletManufacturer;
        this.details = details;
    }

    public Tablet(String title, int count, double price, String model, TabletManufacturer tabletManufacturer) {
        super(title, count, price, ProductType.TABLET);
        this.model = model;
        this.tabletManufacturer = tabletManufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tablet tablet = (Tablet) o;
        return Objects.equals(id, tablet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
