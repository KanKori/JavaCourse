package com;

import com.model.invoice.Invoice;
import com.model.product.AbstractProduct;
import com.repository.invoice.database.InvoiceRepositoryDB;
import com.repository.product.database.laptop.LaptopRepositoryDB;
import com.repository.product.database.phone.PhoneRepositoryDB;
import com.repository.product.database.tablet.TabletRepositoryDB;
import com.service.invoice.database.InvoiceServiceDB;
import com.service.product.laptop.database.LaptopServiceDB;
import com.service.product.phone.database.PhoneServiceDB;
import com.service.product.tablet.database.TabletServiceDB;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        InvoiceRepositoryDB invoiceRepositoryDB = new InvoiceRepositoryDB();
        InvoiceServiceDB invoiceServiceDB = new InvoiceServiceDB(invoiceRepositoryDB);
        PhoneRepositoryDB phoneRepositoryDB = new PhoneRepositoryDB();
        PhoneServiceDB phoneServiceDB = new PhoneServiceDB(phoneRepositoryDB);
/*        TabletRepositoryDB tabletRepositoryDB = new TabletRepositoryDB();
        TabletServiceDB tabletServiceDB = new TabletServiceDB(tabletRepositoryDB);
        LaptopRepositoryDB laptopRepositoryDB = new LaptopRepositoryDB();
        LaptopServiceDB laptopServiceDB = new LaptopServiceDB(laptopRepositoryDB);*/
        List<String> products = new ArrayList<>(3);
        products.add("INSERT INTO \"public\".\"Phone\" (id) VALUES (?)");
        Invoice<AbstractProduct> invoice = new Invoice<>();
        invoiceRepositoryDB.createProducts(products, invoice);
        invoiceRepositoryDB.save(invoice);
    }
}