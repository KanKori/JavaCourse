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
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Invoice<T extends AbstractProduct> {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column
    private double sum;
    @OneToMany(mappedBy = "invoice",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private List<T> products;
    @Column
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
