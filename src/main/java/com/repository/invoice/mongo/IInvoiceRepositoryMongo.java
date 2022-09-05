package com.repository.invoice.mongo;

import com.model.invoice.Invoice;

import java.util.List;
import java.util.Optional;

public interface IInvoiceRepositoryMongo {
    boolean save(Invoice invoice);

    boolean update(Invoice invoice);

    boolean delete(String id);

    Optional<Invoice> findById(String id);

    List<Invoice> getInvoicesCostlyThanPrice(double price);

    int getInvoiceCount();

    void sortBySum();
}
