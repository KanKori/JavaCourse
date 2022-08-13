package com.service.statistics;

import com.model.product.specifications.ProductType;
import com.service.shop.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsServiceTest {
    private StatisticsService target;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        shopService = Mockito.mock(ShopService.class);
        target = new StatisticsService(shopService.getInvoiceList());
    }

    @Test
    void productsCountByType() {
        assertNotNull(target.productsCountByType(ProductType.TELEVISION));
    }

    @Test
    void amountOfRetail() {
        assertNotNull(target.amountOfRetail());
    }

    @Test
    void lowestSumInvoice() {
        assertNotNull(target.lowestSumInvoice());
    }

    @Test
    void sumAllInvoices() {
        assertNotNull(target.sumAllInvoices());
    }
}