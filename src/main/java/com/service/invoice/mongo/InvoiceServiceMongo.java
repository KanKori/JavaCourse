package com.service.invoice.mongo;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.repository.invoice.mongo.InvoiceRepositoryMongo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceServiceMongo {
    private final InvoiceRepositoryMongo invoiceRepositoryMongo;

    public InvoiceServiceMongo(InvoiceRepositoryMongo invoiceRepositoryMongo) {
        this.invoiceRepositoryMongo = invoiceRepositoryMongo;
    }

    public void createAndSaveInvoiceFromList(List<AbstractProduct> invoiceProducts) {
        Invoice invoice = new Invoice();
        invoice.setLocalDateTime(LocalDateTime.now());
        invoice.setSum(invoiceProducts.stream().mapToDouble(AbstractProduct::getPrice).sum());
        invoice.setProducts(new ArrayList<>(invoiceProducts));
        invoiceRepositoryMongo.save(invoice);
    }

    public List<Invoice> getInvoicesCostlyThanPrice(double price) {
        return invoiceRepositoryMongo.getInvoicesCostlyThanPrice(price);
    }

    public int getInvoiceCount() {
        return invoiceRepositoryMongo.getInvoiceCount();
    }

    public void updateInvoice(Invoice inputInvoice) {
        invoiceRepositoryMongo.update(inputInvoice);
    }

    public void sortBySum() {
        invoiceRepositoryMongo.sortBySum();
    }
}
