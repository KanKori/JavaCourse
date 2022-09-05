package com.model.product.phone;

import com.model.invoice.Invoice;
import com.model.product.phone.specifications.PhoneManufacturer;
import com.model.product.AbstractProduct;
import com.model.product.specifications.ProductType;
import com.model.operating_system.OperatingSystem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Phone extends AbstractProduct {
    @Column
    private String model;
    @Column
    @Enumerated(EnumType.STRING)
    private PhoneManufacturer phoneManufacturer;
    @Transient
    private List<String> details;
    @Transient
    private String currency;
    @Transient
    private LocalDateTime creatingDate;
    @Transient
    private OperatingSystem OS;

    public Phone(String title, String model,
                 double price, String currency,
                 PhoneManufacturer phoneManufacturer,
                 LocalDateTime creatingDate,
                 int count, OperatingSystem OS) {
        super(title, count, price, ProductType.PHONE);
        this.model = model;
        this.phoneManufacturer = phoneManufacturer;
        this.currency = currency;
        this.creatingDate = creatingDate;
        this.OS = OS;
    }

    public Phone(String title, int count,
                 double price, String model,
                 PhoneManufacturer phoneManufacturer) {
        super(title, count, price, ProductType.PHONE);
        this.model = model;
        this.phoneManufacturer = phoneManufacturer;
    }

    public Phone(String id, String title, int count, double price, String model, PhoneManufacturer phoneManufacturer) {
        super(title, count, price, ProductType.PHONE);
        this.id = id;
        this.model = model;
        this.phoneManufacturer = phoneManufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(id, phone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Phone {" + "\n" +
                "manufacturer=" + phoneManufacturer + "\n" +
                "id='" + id + '\'' + "\n" +
                "title='" + title + '\'' + "\n" +
                "count=" + count + "\n" +
                "price=" + price + currency + "\n" +
                '}' + "\n";
    }
}
