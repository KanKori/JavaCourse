package com.model.invoice;

import com.model.customer.Customer;
import com.model.invoice.specifications.InvoiceType;
import com.model.product.AbstractProduct;

import java.time.LocalDateTime;
import java.util.List;

public class Invoice<T extends AbstractProduct> {
    private List<T> products;
    private Customer customer;
    private InvoiceType type;
    private LocalDateTime createdTime;
}
