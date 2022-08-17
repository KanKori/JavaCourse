package com.model.invoice;

import com.model.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class Invoice {
    private String id;
    private double sum;
    private List<Product> products;
    private LocalDateTime time;

    public Invoice(String id, double sum, List<Product> products, LocalDateTime time) {
        this.id = id;
        this.sum = sum;
        this.products = products;
        this.time = time;
    }
}
