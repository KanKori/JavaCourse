package com;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.model.product.specifications.ProductType;
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
        ShopService shopService = new ShopService(".\\src\\module\\src\\main\\resources\\products.csv", sumLimit);
        System.out.println();
        shopService.createAndSaveRandomInvoice(15);
        List<Invoice<AbstractProduct>> invoiceList = shopService.getInvoiceList();
        for (Object invoice : invoiceList) {
            System.out.println(invoice);
        }
        System.out.println("~~~~~~~~~~~~STATISTICS SERVICE~~~~~~~~~~~~");
        StatisticsService statisticsService = new StatisticsService(invoiceList);
        System.out.println("~~~~~~~~~~~~printProductsCountByType~~~~~~~~~~~~");
        System.out.println("\nCOUNT OF PRODUCTS BY TELEPHONE TYPE = "
                + statisticsService.productsCountByType(ProductType.TELEPHONE)
                + "\nCOUNT OF PRODUCTS BY TELEVISION TYPE = "
                + statisticsService.productsCountByType(ProductType.TELEVISION));
        System.out.println("~~~~~~~~~~~~printLowestSumInvoice~~~~~~~~~~~~");
        System.out.println("INVOICE WITH LOWEST SUM : " + statisticsService.lowestSumInvoice());
        System.out.println("~~~~~~~~~~~~printSumAllInvoices~~~~~~~~~~~~");
        System.out.println("\nSUM ALL : " + statisticsService.sumAllInvoices());
        System.out.println("~~~~~~~~~~~~printAmountOfRetail()~~~~~~~~~~~~");
        System.out.println("\nAMOUNT OF RETAIL INVOICES : " + statisticsService.amountOfRetail());
        System.out.println("~~~~~~~~~~~~printInvoicesWithSingleProductType~~~~~~~~~~~~");
        System.out.println("INVOICES WITH SINGLE PRODUCT TYPE:");
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
