package com;

import com.model.Laptop;
import com.model.Phone;
import com.model.Product;
import com.model.ProductType;
import com.model.Tablet;
import com.repository.LaptopRepository;
import com.repository.PhoneRepository;
import com.repository.TabletRepository;
import com.service.LaptopService;
import com.service.PhoneService;
import com.service.ProductFactory;
import com.service.TabletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {
    private static final PhoneService PHONE_SERVICE = new PhoneService(new PhoneRepository());
    private static final LaptopService LAPTOP_SERVICE = new LaptopService(new LaptopRepository());


    public static void main(String[] args) {
        PHONE_SERVICE.createAndSavePhones(10);
        PHONE_SERVICE.printAll();
        LAPTOP_SERVICE.createAndSaveLaptops(10);
        LAPTOP_SERVICE.printAll();

  /*      List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Phone phone = (Phone) ProductFactory.createProduct(ProductType.PHONE);
            PHONE_SERVICE.savePhone(phone);
            products.add(phone);
        }
        System.out.println(products);
    */
    }
}
