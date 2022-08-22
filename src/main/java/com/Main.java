package com;

import com.model.product.phone.Phone;
import com.repository.product.database.phone.PhoneRepositoryDB;
import com.service.product.phone.database.PhoneServiceDB;

public class Main {
    public static void main(String[] args) {
        PhoneRepositoryDB phoneRepositoryDB = new PhoneRepositoryDB();
        PhoneServiceDB phoneServiceDB = new PhoneServiceDB(phoneRepositoryDB);
        Phone phone = phoneServiceDB.createProduct();
        phoneRepositoryDB.save(phone);
    }
}