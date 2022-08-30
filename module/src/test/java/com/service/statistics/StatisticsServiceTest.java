package com.service.statistics;

import com.service.shop.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class StatisticsServiceTest {
    private StatisticsService target;

    @BeforeEach
    void setUp() {
        ShopService shopService = Mockito.mock(ShopService.class);
        target = new StatisticsService(shopService.getInvoiceList());
    }

    @Test
    void lowestSumInvoice_assertNotNull() {
        assertNotNull(target.getLowestSumInvoice());
    }

    @Test
    void invoicesWithSingleProductType_assertNotNull() {
        assertNotNull(target.getInvoicesWithSingleProductType());
    }

    @Test
    void firstThreeInvoices_assertNotNull() {
        assertNotNull(target.getFirstThreeInvoices());
    }

    @Test
    void invoicesByPersonsUnderAdultAge_assertNotNull() {
        assertNotNull(target.getInvoicesByPersonsUnderAdultAge());
    }

    @Test
    void sortedInvoices_assertNotNull() {
        assertNotNull(target.getSortedInvoices());
    }
}