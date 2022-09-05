package com.model.invoice;

import com.model.product.AbstractProduct;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Invoice {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column
    private double sum;
    @OneToMany(/*mappedBy = "invoice",*/
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "Invoice_Products",
            joinColumns = {@JoinColumn(name = "invoice", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "product", referencedColumnName = "id")}
    )
    private List<AbstractProduct> products;
    @Column
    private LocalDateTime localDateTime;

    public Invoice(double sum, List<AbstractProduct> products, LocalDateTime localDateTime) {
        this.sum = sum;
        this.products = products;
        this.localDateTime = localDateTime;
    }

    public Invoice(String id, double sum, List<AbstractProduct> products, LocalDateTime localDateTime) {
        this.id = id;
        this.sum = sum;
        this.products = products;
        this.localDateTime = localDateTime;
    }

    public Invoice() {
        //id = UUID.randomUUID().toString();
    }
}
