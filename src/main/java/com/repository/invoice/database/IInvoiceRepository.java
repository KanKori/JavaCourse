package com.repository.invoice.database;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface IInvoiceRepository<T extends AbstractProduct> {
    Invoice<T> createFromResultSet(ResultSet resultSet);

    void save(Invoice<T> invoice);

    Optional<Invoice<T>> findById(String id);

    List<Invoice<T>> findAll();

    boolean delete(String id);
}
