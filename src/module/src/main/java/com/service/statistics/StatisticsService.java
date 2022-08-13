package com.service.statistics;

import com.model.invoice.Invoice;
import com.model.invoice.specifications.InvoiceType;
import com.model.product.AbstractProduct;
import com.model.product.specifications.ProductType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StatisticsService {
    private final List<Invoice<AbstractProduct>> invoiceList;

    public StatisticsService(List<Invoice<AbstractProduct>> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public long productsCountByType(ProductType type) {
        return invoiceList.stream()
                .flatMap(invoice -> invoice.getProducts().stream())
                .filter(invoice -> invoice.getType().equals(type))
                .count();
    }

    public double sumAllInvoices() {
        return invoiceList.stream()
                .mapToDouble(Invoice::getSum)
                .sum();
    }

    public Optional<Invoice<AbstractProduct>> lowestSumInvoice() {
        return invoiceList.stream()
                .sorted(Comparator.comparing(Invoice::getSum))
                .toList()
                .stream()
                .findFirst();
    }

    public int amountOfRetail() {
        return Math.toIntExact(invoiceList.stream()
                .filter(invoice -> invoice.getType().contains(InvoiceType.retail))
                .count());
    }

    public void printInvoicesWithSingleProductType() {
        System.out.println("INVOICES WITH SINGLE PRODUCT TYPE:");
        List<Invoice<AbstractProduct>> singleType = new ArrayList<>();
        invoiceList.forEach(invoice -> {
            if (invoice.getProducts().stream()
                    .allMatch(product -> product.getType().equals(ProductType.TELEVISION))) {
                singleType.add(invoice);
            }
            if (invoice.getProducts().stream()
                    .allMatch(product -> product.getType().equals(ProductType.TELEPHONE))) {
                singleType.add(invoice);
            }
        });
        if (singleType.isEmpty()) {
            System.out.println("Theres no one");
        } else {
            singleType.forEach(System.out::println);
        }
    }

    public void printFirstThreeInvoices() {
        final int THREE_INVOICES = 3;
        System.out.println("FIRST 3 INVOICES: ");
        invoiceList.stream()
                .sorted(Comparator.comparing(Invoice::getCreatedTime))
                .limit(THREE_INVOICES)
                .forEach(System.out::println);
    }

    public void printInvoicesByPersonsUnder18Age() {
        System.out.println("\nINVOICES OF PERSON UNDER 18 AGE : ");
        invoiceList.stream()
                .filter(invoice -> invoice.getType().contains(InvoiceType.low_age))
                .forEach(System.out::println);
    }

    public void printSortedInvoices() {
        System.out.println("SORTED INVOICES : ");
        Comparator<Invoice<AbstractProduct>> compareByAge = Comparator.comparing
                (invoice -> invoice.getCustomer().getAge(), Comparator.reverseOrder());
        Comparator<Invoice<AbstractProduct>> compareByProductsListSize = Comparator.comparing
                (invoice -> invoice.getProducts().size());
        Comparator<Invoice<AbstractProduct>> compareByInvoiceSum = Comparator.comparing(Invoice::getSum);

        invoiceList.stream()
                .sorted(compareByAge
                        .thenComparing(compareByProductsListSize)
                        .thenComparing(compareByInvoiceSum))
                .forEach(System.out::println);
    }
}