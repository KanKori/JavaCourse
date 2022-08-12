package com;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.service.ShopService;

import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please, entry sum limit for invoices");
        final Scanner scanner = new Scanner(System.in);
        final double sumLimit = Double.parseDouble(scanner.next());
        ShopService shopService = new ShopService();
        System.out.println();
        shopService.createAndSaveRandomInvoice(15, sumLimit);
        Queue<Invoice<AbstractProduct>> invoiceQueue = shopService.getInvoiceQueue();
        for (Object invoice : invoiceQueue) {
            System.out.println(invoice);
        }
    }
}
