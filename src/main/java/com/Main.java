package com;

import com.model.product.AbstractProduct;
import com.repository.invoice.database.InvoiceRepositoryDB;
import com.repository.product.database.phone.PhoneRepositoryDB;
import com.service.invoice.database.InvoiceServiceDB;
import com.service.product.phone.database.PhoneServiceDB;

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
        int inte = invoiceServiceDB.getInvoiceCount();
    }
}