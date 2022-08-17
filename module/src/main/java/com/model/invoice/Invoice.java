package com.model.invoice;

import com.model.customer.Customer;
import com.model.invoice.specifications.InvoiceType;
import com.model.product.AbstractProduct;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class Invoice<T extends AbstractProduct> {
    private String type;
    private List<T> products;
    private Customer customer;
    private LocalDateTime createdTime;
    private Double sum;

    public Invoice(List<T> products, Customer customer, LocalDateTime createdTime, double sumLimit) {
        this.products = products;
        this.customer = customer;
        this.createdTime = createdTime;
        this.sum = products.stream()
                .mapToDouble(AbstractProduct::getPrice)
                .sum();
        this.type = String.valueOf((sum > sumLimit) ? InvoiceType.RETAIL : InvoiceType.WHOLESALE);
    }

    @Override
    public String toString() {
        return "Invoice: {" + "\n" +
                "Type " + type + "\n" +
                "Products " + products + "; " +
                "\n" + customer + "; " +
                "\nCreated Time " + createdTime + "; " +
                "\nTotal sum " + sum +
                "\n}" + "\n";
    }
}
