package com.repository.invoice;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class InvoiceRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepository.class);
    private final List<Invoice<AbstractProduct>> invoices;

    public InvoiceRepository() {
        invoices = new LinkedList<>();
    }

    public void save(Invoice<AbstractProduct> invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Cannot save a null invoice");
        } else {
            checkDuplicates(invoice);
        }
        invoices.add(invoice);
    }

    private void checkDuplicates(Invoice<AbstractProduct> invoice) {
        for (Invoice<AbstractProduct> currentInvoice : invoices) {
            if (invoice.hashCode() == currentInvoice.hashCode() && invoice.equals(currentInvoice)) {
                final IllegalArgumentException exception = new IllegalArgumentException("Duplicate invoice: " + invoice.getId());
                LOGGER.error(exception.getMessage(), exception);
                throw exception;
            }
        }
    }

    public void saveAll(List<Invoice<AbstractProduct>> invoices) {
        for (Invoice<AbstractProduct> invoice : invoices) {
            save(invoice);
        }
    }

    public boolean update(Invoice<AbstractProduct> invoice) {
        final Optional<Invoice<AbstractProduct>> result = findById(invoice.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Invoice<AbstractProduct> originalInvoice = result.get();
        InvoiceRepository.invoiceCopy.copy(invoice, originalInvoice);
        return true;
    }

    public Optional<Invoice<AbstractProduct>> findById(String id) {
        Invoice<AbstractProduct> result = null;
        for (Invoice<AbstractProduct> invoice : invoices) {
            if (invoice.getId().equals(id)) {
                result = invoice;
            }
        }
        return Optional.ofNullable(result);
    }

    private static class invoiceCopy {
        private static void copy(final Invoice<AbstractProduct> from, final Invoice<AbstractProduct> to) {
            to.setId(from.getId());
            to.setSum(from.getSum());
            to.setProducts(from.getProducts());
            to.setTime(from.getTime());
        }
    }
}
