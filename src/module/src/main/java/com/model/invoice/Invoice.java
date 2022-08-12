package com.model.invoice;

import com.model.customer.Customer;
import com.model.invoice.specifications.InvoiceType;
import com.model.product.AbstractProduct;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class Invoice<T extends AbstractProduct> {
    private InvoiceType type;

    public Invoice(List<T> products, Customer customer, LocalDateTime createdTime) {
    }
}
