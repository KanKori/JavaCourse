package com.repository.invoice.hibernate;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IInvoiceRepositoryHibernate {
    void save(Invoice invoice);

    Optional<Invoice> findById(String id);

    boolean update(Invoice invoice);

    List<Invoice> getInvoicesCostlyThanPrice(double sum);

    int getInvoiceCount();

    Map<Double, Integer> sortBySum();
}
