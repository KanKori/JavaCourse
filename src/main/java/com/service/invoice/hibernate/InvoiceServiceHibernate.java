package com.service.invoice.hibernate;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.repository.invoice.hibernate.InvoiceRepositoryHibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvoiceServiceHibernate {
    private final InvoiceRepositoryHibernate invoiceRepositoryHibernate;

    public InvoiceServiceHibernate(InvoiceRepositoryHibernate invoiceRepositoryHibernate) {
        this.invoiceRepositoryHibernate = invoiceRepositoryHibernate;
    }

    public void createAndSaveInvoiceFromList(List<AbstractProduct> invoiceProducts) {
        Invoice invoice = new Invoice();
        invoice.setLocalDateTime(LocalDateTime.now());
        invoice.setSum(invoiceProducts.stream().mapToDouble(AbstractProduct::getPrice).sum());
        invoice.setProducts(new ArrayList<>(invoiceProducts));
        invoiceRepositoryHibernate.save(invoice);
    }

    public List<Invoice> getInvoicesCostlyThanPrice(double price) {
        return invoiceRepositoryHibernate.getInvoicesCostlyThanPrice(price);
    }

    public int getInvoiceCount() {
        return invoiceRepositoryHibernate.getInvoiceCount();
    }

    public void update(Invoice invoice) {
        invoiceRepositoryHibernate.update(invoice);
    }

    public Map<Double, Integer> sortBySum() {
        return invoiceRepositoryHibernate.sortBySum();
    }
}
