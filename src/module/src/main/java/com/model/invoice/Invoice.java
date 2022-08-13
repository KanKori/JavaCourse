package com.model.invoice;

import com.model.customer.Customer;
import com.model.invoice.specifications.InvoiceType;
import com.model.product.AbstractProduct;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Invoice<T extends AbstractProduct> {
    private List<InvoiceType> type = new ArrayList<>();
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
        this.type.add(
                (sum > sumLimit) ? InvoiceType.retail : InvoiceType.wholesale);
        if (customer.getAge() < 18) {
            this.type.add(InvoiceType.low_age);
        }
    }

    @Override
    public String toString() {
        return "Invoice: {" +
                "Type " + type + "\n" +
                "Products " + products + "; " +
                "\n" + customer + "; " +
                "\nCreated Time " + createdTime + "; " +
                "\nTotal sum " + sum +
                "\n}" + "\n";
    }
}
