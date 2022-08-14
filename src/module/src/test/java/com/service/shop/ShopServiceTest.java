package com.service.shop;

import com.exception.file.read.InvalidLineException;
import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShopServiceTest {
    private ShopService target;

    @BeforeEach
    void setUp() throws InvalidLineException {
        double sumLimit = 2000;
        target = new ShopService(".\\src\\main\\resources\\products.csv", sumLimit);
    }

    @Test
    void getInvoiceList() {
        List<Invoice<AbstractProduct>> targetInvoiceList = target.getInvoiceList();
        assertEquals(targetInvoiceList, target.getInvoiceList());
    }

    @Test
    void createAndSaveRandomInvoice() {
        target.createAndSaveRandomInvoice(2);
        assertThat(target.getInvoiceList()).isNotNull();
    }

}