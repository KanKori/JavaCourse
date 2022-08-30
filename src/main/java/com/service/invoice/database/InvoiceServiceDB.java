package com.service.invoice.database;

import com.config.JDBCConfig;
import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.repository.invoice.database.InvoiceRepositoryDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvoiceServiceDB {
    private final InvoiceRepositoryDB invoiceRepositoryDB;

    public InvoiceServiceDB(InvoiceRepositoryDB invoiceRepositoryDB) {
        this.invoiceRepositoryDB = invoiceRepositoryDB;
    }

    public void createAndSaveInvoiceFromList(List<AbstractProduct> invoiceProducts) {
        Invoice<AbstractProduct> invoice = new Invoice<>();
        invoice.setLocalDateTime(LocalDateTime.now());
        invoice.setSum(invoiceProducts.stream().mapToDouble(AbstractProduct::getPrice).sum());
        invoice.setProducts(new ArrayList<>(invoiceProducts));
        invoiceRepositoryDB.save(invoice);
    }

    public List<Invoice<AbstractProduct>> getInvoicesCostlyThanPrice(double price) {
       return invoiceRepositoryDB.getInvoicesCostlyThanPrice(price);
    }

    public int getInvoiceCount() {
        return invoiceRepositoryDB.getInvoiceCount();
    }

    public void updateInvoiceDateTime(String id, LocalDateTime dateTime) {
        invoiceRepositoryDB.updateInvoiceDateTime(id, dateTime);
    }

    public Map<Double, Double> groupBySum() {
        return invoiceRepositoryDB.groupBySum();
    }
}