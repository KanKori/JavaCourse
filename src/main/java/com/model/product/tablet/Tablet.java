package com.model.product.tablet;

import com.model.product.Product;
import com.model.product.specifications.ProductType;
import com.model.product.tablet.specifications.TabletManufacturer;
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
        return "Tablet{" + "manufacturer = " + tabletManufacturer + "\n" +
                ", id = " + id + '\'' + "\n" +
                ", title='" + title + '\'' + "\n" +
                ", count=" + count + "\n" +
                ", price=" + price + "\n" +
                '}' + "\n";
    }

    public static class Builder {
        private final Tablet tablet;

        public Builder(double price, TabletManufacturer manufacturer) {
            if (manufacturer == null) {
                throw new IllegalArgumentException("Manufacturer cant be null");
            }
            tablet = new Tablet("DefaultTitle", 0, price, "DefaultModel", manufacturer);
        }

        public Builder setTitle(String title) {
            if (title.length() > 20) {
                throw new IllegalArgumentException("Title cant be more then 20 symbols");
            }
            tablet.setTitle(title);
            return this;
        }

        public Builder setCount(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("Count must be < 0");
            }
            tablet.setCount(count);
            return this;
        }

        public Builder setPrice(double price) {
            tablet.setPrice(price);
            return this;
        }

        public Tablet buildTablet() {
            return tablet;
        }
    }
}
