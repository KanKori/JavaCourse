package com;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.service.shop.ShopService;
import com.service.statistics.StatisticsService;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Please enter check limit");
        final Scanner scanner = new Scanner(System.in);
        final double sumLimit = Double.parseDouble(scanner.next());
        ShopService shopService = new ShopService(".\\src\\module\\src\\main\\resources\\products.csv");
        System.out.println();
        shopService.createAndSaveRandomInvoice(15, sumLimit);
        List<Invoice<AbstractProduct>> invoiceList = shopService.getInvoiceList();
        for (Object invoice : invoiceList) {
            System.out.println(invoice);
        }
        System.out.println("~~~~~~~~~~~~STATISTICS SERVICE~~~~~~~~~~~~");
        StatisticsService statisticsService = new StatisticsService(invoiceList);
        System.out.println("~~~~~~~~~~~~printProductsCountByType~~~~~~~~~~~~");
        statisticsService.printProductsCountByType();
        System.out.println("~~~~~~~~~~~~printLowestSumInvoice~~~~~~~~~~~~");
        statisticsService.printLowestSumInvoice();
        System.out.println("~~~~~~~~~~~~printSumAllInvoices~~~~~~~~~~~~");
        statisticsService.printSumAllInvoices();
        System.out.println("~~~~~~~~~~~~printAmountOfRetail()~~~~~~~~~~~~");
        statisticsService.printAmountOfRetail();
        System.out.println("~~~~~~~~~~~~printInvoicesWithSingleProductType~~~~~~~~~~~~");
        statisticsService.printInvoicesWithSingleProductType();
        System.out.println("~~~~~~~~~~~~printFirstThreeInvoices~~~~~~~~~~~~");
        statisticsService.printFirstThreeInvoices();
        System.out.println("~~~~~~~~~~~~printInvoicesByPersonsUnder18Age~~~~~~~~~~~~");
        statisticsService.printInvoicesByPersonsUnder18Age();
        System.out.println("~~~~~~~~~~~~printSortedInvoices~~~~~~~~~~~~");
        statisticsService.printSortedInvoices();
        System.out.println("~~~~~~~~~~~~STATISTICS SERVICE~~~~~~~~~~~~");
    }
}
