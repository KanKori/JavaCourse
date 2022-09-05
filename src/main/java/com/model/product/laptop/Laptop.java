package com.model.product.laptop;

import com.model.invoice.Invoice;
import com.model.product.laptop.specifications.LaptopManufacturer;
import com.model.product.AbstractProduct;
import com.model.product.specifications.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Laptop extends AbstractProduct {

    @Column
    private String model;
    @Enumerated(EnumType.STRING)
    private LaptopManufacturer laptopManufacturer;

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
