package com.model.invoice;

import com.model.product.AbstractProduct;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class Invoice<T extends AbstractProduct> {
    private String id;
    private double sum;
    private List<T> products;
    private LocalDateTime localDateTime;

    public Invoice(String id, double sum, List<T> products, LocalDateTime localDateTime) {
        this.id = id;
        this.sum = sum;
        this.products = products;
        this.localDateTime = localDateTime;
    }

    public Invoice() {
        id = UUID.randomUUID().toString();
    }
}
