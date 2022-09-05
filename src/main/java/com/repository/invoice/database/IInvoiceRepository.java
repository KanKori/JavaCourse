package com.repository.invoice.database;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface IInvoiceRepository {
    Invoice createFromResultSet(ResultSet resultSet);

    void save(Invoice invoice);

    Optional<Invoice> findById(String id);

    List<Invoice> findAll();

    boolean delete(String id);
}
