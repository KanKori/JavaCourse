package com.service;

import com.model.*;

import java.util.Random;

public class ProductFactory {
    private static final Random RANDOM = new Random();
    private static final ProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final ProductService<Laptop> LAPTOP_SERVICE = LaptopService.getInstance();
    private static final ProductService<Tablet> TABLET_SERVICE = TabletService.getInstance();


    private ProductFactory() {
    }

    public static void createAndSave(ProductType type) {
        switch (type) {
            case PHONE -> PHONE_SERVICE.createAndSaveProducts(1);
            case TABLET -> TABLET_SERVICE.createAndSaveProducts(1);
            case LAPTOP -> LAPTOP_SERVICE.createAndSaveProducts(1);
            default -> throw new IllegalArgumentException("Unknown Product type: " + type);
        }
    }
}