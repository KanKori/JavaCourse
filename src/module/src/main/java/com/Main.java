package com;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.service.ShopService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
    }
}
