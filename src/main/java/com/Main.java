package com;

import com.model.product.AbstractProduct;
import com.repository.invoice.database.InvoiceRepositoryDB;
import com.repository.invoice.hibernate.InvoiceRepositoryHibernate;
import com.repository.product.database.phone.PhoneRepositoryDB;
import com.repository.product.hibernate.phone.PhoneRepositoryHibernate;
import com.service.invoice.database.InvoiceServiceDB;
import com.service.invoice.hibernate.InvoiceServiceHibernate;
import com.service.product.phone.database.PhoneServiceDB;
import com.service.product.phone.hibernate.PhoneServiceHibernate;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        InvoiceRepositoryDB invoiceRepositoryDB = new InvoiceRepositoryDB();
        InvoiceServiceDB invoiceServiceDB = new InvoiceServiceDB(invoiceRepositoryDB);
        PhoneRepositoryDB phoneRepositoryDB = new PhoneRepositoryDB();
        PhoneServiceDB phoneServiceDB = new PhoneServiceDB(phoneRepositoryDB);

        List<AbstractProduct> products = new ArrayList<>();
        products.add(phoneServiceDB.createProduct());
        products.add(phoneServiceDB.createProduct());

        invoiceServiceDB.createAndSaveInvoiceFromList(products);
        invoiceServiceDB.createAndSaveInvoiceFromList(products);


        InvoiceRepositoryHibernate invoiceRepositoryHibernate = new InvoiceRepositoryHibernate();
        InvoiceServiceHibernate invoiceServiceHibernate = new InvoiceServiceHibernate(invoiceRepositoryHibernate);
        PhoneRepositoryHibernate phoneRepositoryHibernate = new PhoneRepositoryHibernate();
        PhoneServiceHibernate phoneServiceHibernate = new PhoneServiceHibernate(phoneRepositoryHibernate);

        List<AbstractProduct> productsHibernate = new ArrayList<>();
        productsHibernate.add(phoneServiceHibernate.createProduct());
        productsHibernate.add(phoneServiceHibernate.createProduct());

        invoiceServiceHibernate.createAndSaveInvoiceFromList(productsHibernate);
        invoiceServiceHibernate.createAndSaveInvoiceFromList(productsHibernate);

    }
}