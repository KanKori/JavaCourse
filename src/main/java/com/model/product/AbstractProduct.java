package com.model.product;

import com.model.invoice.Invoice;
import com.model.product.specifications.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public abstract class AbstractProduct {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    protected String id;
    @Column
    protected String title;
    @Column
    protected int count;
    @Column
    protected double price;
    @Column
    protected ProductType type;

    @ManyToOne
    @JoinColumn(name = "Invoice_id")
    protected Invoice invoice;

    protected AbstractProduct(String title, int count, double price, ProductType type) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.count = count;
        this.price = price;
        this.type = type;
    }
}
