package com;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.service.ShopService;
import com.service.StatisticsService;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Please enter check limit");
        final Scanner scanner = new Scanner(System.in);
        final double sumLimit = Double.parseDouble(scanner.next());
        ShopService shopService = new ShopService();
        System.out.println();
        shopService.createAndSaveRandomInvoice(15, sumLimit);
        List<Invoice<AbstractProduct>> invoiceList = shopService.getInvoiceList();
        for (Object invoice : invoiceList) {
            System.out.println(invoice);
        }
        StatisticsService statisticsService = new StatisticsService(invoiceList);
        statisticsService.printProductsCountByType();
        statisticsService.printLowestSumInvoice();
        statisticsService.printSumAllInvoices();
        statisticsService.printAmountOfRetail();
        statisticsService.printInvoicesWithSingleProductType();
        statisticsService.printFirstThreeInvoices();
        statisticsService.printInvoicesByPersonsUnder18Age();
        statisticsService.printSortedInvoices();
    }
}
