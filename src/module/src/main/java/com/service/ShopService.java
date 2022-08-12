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
import java.util.Queue;
import java.util.Random;

public class ShopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);
    private static final List<AbstractProduct> productList;

    static {
        try {
            productList = new Parser().parseCSV();
        } catch (InvalidLineException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Random RANDOM = new Random();
    private static final PersonService PERSON_SERVICE = new PersonService();
    private static final Queue<Invoice<AbstractProduct>> invoiceQueue = new LinkedList<>();

    public Queue<Invoice<AbstractProduct>> getInvoiceQueue() {
        return invoiceQueue;
    }

    private Invoice<AbstractProduct> createRandomInvoice(double sumLimit) {
        List<AbstractProduct> invoiceProducts = new ArrayList<>();
        final int RANDOM_AMOUNT_OF_PRODUCT = RANDOM.nextInt(1, 5);
        for (int i = 0; i < RANDOM_AMOUNT_OF_PRODUCT; i++) {
            AbstractProduct productFromDefaultList = productList.get(RANDOM.nextInt(productList.size()));
            invoiceProducts.add(productFromDefaultList);
        }
        return new Invoice<>(invoiceProducts,
                PERSON_SERVICE.createRandomCustomer(),
                LocalDateTime.now(), sumLimit);
    }

    public void createAndSaveRandomInvoice(int amountOfInvoice, double sumLimit) {
        for (int i = 0; i < amountOfInvoice; i++) {
            invoiceQueue.add(createRandomInvoice(sumLimit));
        }
    }
}