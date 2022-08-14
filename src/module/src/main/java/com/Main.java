package com;

import com.exception.file.read.InvalidLineException;
import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.model.product.specifications.ProductType;
import com.service.shop.ShopService;
import com.service.statistics.StatisticsService;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InvalidLineException {
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
        System.out.println("\nINVOICE WITH LOWEST SUM : \n" + statisticsService.lowestSumInvoice());
        System.out.println("~~~~~~~~~~~~printSumAllInvoices~~~~~~~~~~~~");
        System.out.println("\nSUM ALL : \n" + statisticsService.sumAllInvoices());
        System.out.println("~~~~~~~~~~~~printAmountOfRetail()~~~~~~~~~~~~");
        System.out.println("\nAMOUNT OF RETAIL INVOICES : " + statisticsService.amountOfRetail());
        System.out.println("~~~~~~~~~~~~printInvoicesWithSingleProductType~~~~~~~~~~~~");
        System.out.println("\nINVOICES WITH SINGLE PRODUCT TYPE:  \n");
        if (statisticsService.invoicesWithSingleProductType() != null) {
            statisticsService.invoicesWithSingleProductType().forEach(System.out::println);
        } else {
            System.out.println("No one found");
        }
        System.out.println("~~~~~~~~~~~~printFirstThreeInvoices~~~~~~~~~~~~");
        System.out.println("\nFIRST 3 INVOICES: \n");
        if (statisticsService.firstThreeInvoices() != null) {
            statisticsService.firstThreeInvoices().forEach(System.out::println);
        } else {
            System.out.println("\nLess than 3 invoices specified");
        }
        System.out.println("~~~~~~~~~~~~printInvoicesByPersonsUnder18Age~~~~~~~~~~~~");
        System.out.println("\nINVOICES OF PERSON UNDER 18 AGE : \n");
        if (statisticsService.invoicesByPersonsUnder18Age() != null) {
            statisticsService.invoicesByPersonsUnder18Age().forEach(System.out::println);
        } else {
            System.out.println("No one found");
        }
        System.out.println("~~~~~~~~~~~~printSortedInvoices~~~~~~~~~~~~");
        System.out.println("\nSORTED INVOICES : \n");
        statisticsService.sortedInvoices().forEach(System.out::println);
        System.out.println("~~~~~~~~~~~~STATISTICS SERVICE~~~~~~~~~~~~");
    }
}
