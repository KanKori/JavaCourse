package com.service.statistics;

import com.model.invoice.Invoice;
import com.model.invoice.specifications.InvoiceType;
import com.model.product.AbstractProduct;
import com.model.product.specifications.ProductType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@Setter
public class StatisticsService {
    private final List<Invoice<AbstractProduct>> invoiceList;

    public StatisticsService(List<Invoice<AbstractProduct>> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public long getProductsCountByType(ProductType type) {
        return invoiceList.stream()
                .flatMap(invoice -> invoice.getProducts().stream())
                .filter(invoice -> invoice.getType().equals(type))
                .count();
    }

    public double getSumAllInvoices() {
        return invoiceList.stream()
                .mapToDouble(Invoice::getSum)
                .sum();
    }

    public Optional<Invoice<AbstractProduct>> getLowestSumInvoice() {
        return invoiceList.stream()
                .sorted(Comparator.comparing(Invoice::getSum))
                .toList()
                .stream()
                .findFirst();
    }

    public int getAmountOfRetail() {
        return Math.toIntExact(invoiceList.stream()
                .filter(invoice -> invoice.getType().contains(InvoiceType.RETAIL.toString()))
                .count());
    }

    public List<Invoice<AbstractProduct>> getInvoicesWithSingleProductType() {
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
        return singleType;
    }

    public Stream<Invoice<AbstractProduct>> getFirstThreeInvoices() {
        final int THREE_INVOICES = 3;
        if (invoiceList.size() > THREE_INVOICES) {
            return invoiceList.stream()
                    .sorted(Comparator.comparing(Invoice::getCreatedTime))
                    .limit(THREE_INVOICES);
        } else {
            return invoiceList.stream()
                    .sorted(Comparator.comparing(Invoice::getCreatedTime));
        }
    }

    public Stream<Invoice<AbstractProduct>> getInvoicesByPersonsUnderAdultAge() {
        final int ADULT_AGE = 18;
        return invoiceList.stream()
                .filter(invoice -> invoice.getCustomer().getAge() < ADULT_AGE)
                .peek(invoice -> invoice.setType(String.valueOf(InvoiceType.LOW_AGE)));
    }

    public Stream<Invoice<AbstractProduct>> getSortedInvoices() {
        Comparator<Invoice<AbstractProduct>> compareByAge = Comparator.comparing
                (invoice -> invoice.getCustomer().getAge(), Comparator.reverseOrder());
        Comparator<Invoice<AbstractProduct>> compareByProductsListSize = Comparator.comparing
                (invoice -> invoice.getProducts().size());
        Comparator<Invoice<AbstractProduct>> compareByInvoiceSum = Comparator.comparing(Invoice::getSum);

        return invoiceList.stream()
                .sorted(compareByAge
                        .thenComparing(compareByProductsListSize)
                        .thenComparing(compareByInvoiceSum));
    }
}