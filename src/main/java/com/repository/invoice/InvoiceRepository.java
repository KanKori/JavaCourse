package com.repository.invoice;

import com.model.invoice.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class InvoiceRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepository.class);
    private final List<Invoice> invoices;

    public InvoiceRepository() {
        invoices = new LinkedList<>();
    }

    public void save(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Cannot save a null invoice");
        } else {
            checkDuplicates(invoice);
        }
        invoices.add(invoice);
    }

    private void checkDuplicates(Invoice invoice) {
        for (Invoice currentInvoice : invoices) {
            if (invoice.hashCode() == currentInvoice.hashCode() && invoice.equals(currentInvoice)) {
                final IllegalArgumentException exception = new IllegalArgumentException("Duplicate invoice: " + invoice.getId());
                LOGGER.error(exception.getMessage(), exception);
                throw exception;
            }
        }
    }

    public void saveAll(List<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            save(invoice);
        }
    }

    public boolean update(Invoice invoice) {
        final Optional<Invoice> result = findById(invoice.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Invoice originalInvoice = result.get();
        InvoiceRepository.invoiceCopy.copy(invoice, originalInvoice);
        return true;
    }

    public Optional<Invoice> findById(String id) {
        Invoice result = null;
        for (Invoice invoice : invoices) {
            if (invoice.getId().equals(id)) {
                result = invoice;
            }
        }
        return Optional.ofNullable(result);
    }

    private static class invoiceCopy {
        private static void copy(final Invoice from, final Invoice to) {
            to.setId(from.getId());
            to.setSum(from.getSum());
            to.setProducts(from.getProducts());
            to.setLocalDateTime(from.getLocalDateTime());
        }
    }
}
