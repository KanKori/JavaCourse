package com.service;

import com.exception.file.read.InvalidLineException;
import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ShopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);
    private static final List<AbstractProduct> ABSTRACT_PRODUCTS;

    static {
        try {
            ABSTRACT_PRODUCTS = new Parser().parseCSV();
        } catch (InvalidLineException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Random RANDOM = new Random();
    private static final PersonService PERSON_SERVICE = new PersonService();
    private static final List<Invoice<AbstractProduct>> INVOICE_LIST = new LinkedList<>();

    public List<Invoice<AbstractProduct>> getInvoiceList() {
        return INVOICE_LIST;
    }

    private Invoice<AbstractProduct> createRandomInvoice(double sumLimit) {
        List<AbstractProduct> invoiceProducts = new ArrayList<>();
        final int RANDOM_AMOUNT_OF_PRODUCT = RANDOM.nextInt(1, 5);
        for (int i = 0; i < RANDOM_AMOUNT_OF_PRODUCT; i++) {
            invoiceProducts.add(ABSTRACT_PRODUCTS.get(RANDOM.nextInt(ABSTRACT_PRODUCTS.size())));
        }
        return new Invoice<>(invoiceProducts,
                PERSON_SERVICE.createRandomCustomer(),
                LocalDateTime.now(), sumLimit);
    }

    public void createAndSaveRandomInvoice(int amountOfInvoice, double sumLimit) {
        for (int i = 0; i < amountOfInvoice; i++) {
            INVOICE_LIST.add(createRandomInvoice(sumLimit));
            LOGGER.info("\n\nTime:\n[{}]\nUser-Data:\n[{}]\n\nAll-Invoice-Data:\n[{}]\n",
                    INVOICE_LIST.get(i).getCreatedTime(),
                    INVOICE_LIST.get(i).getCustomer(), INVOICE_LIST);
        }
    }
}