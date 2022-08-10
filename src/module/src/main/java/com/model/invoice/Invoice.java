package com.model.invoice;

import com.model.customer.Customer;
import com.model.product.Product;

import java.time.LocalDateTime;
import java.util.List;

public class Invoice<T extends Product> {
    private List<T> products;
    private Customer customer;
    private InvoiceType type;
    private LocalDateTime createdTime;
}
