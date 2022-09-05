package com.repository.invoice.hibernate;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IInvoiceRepositoryHibernate {
    void save(Invoice<AbstractProduct> invoice);

    Optional<Invoice> findById(String id);

    boolean update(Invoice<AbstractProduct> invoice);

    List<Invoice> findAllGreaterThanInputSumInvoices(double sum);

    int getInvoiceCount();

    Map<Double, Integer> sortBySum();
}
