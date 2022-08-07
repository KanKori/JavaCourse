package com.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Phone extends Product {
    private final String model;
    private final PhoneManufacturer phoneManufacturer;
    private List<String> details;
    private String currency;
    private LocalDateTime creatingDate;
    private OperatingSystem OS;

    public Phone(String title, int count,
                 double price, String model,
                 PhoneManufacturer phoneManufacturer,
                 List<String> details, String currency,
                 LocalDateTime creatingDate, OperatingSystem OS) {
        super(title, count, price, ProductType.PHONE);
        this.model = model;
        this.phoneManufacturer = phoneManufacturer;
        this.details = details;
        this.currency = currency;
        this.creatingDate = creatingDate;
        this.OS = OS;
    }

    public Phone(String title, int count, double price, String model, PhoneManufacturer phoneManufacturer) {
        super(title, count, price, ProductType.PHONE);
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
        return "Phone{" +
                "manufacturer=" + phoneManufacturer +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
