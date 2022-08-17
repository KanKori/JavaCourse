package com.service.product;

import com.model.product.laptop.Laptop;
import com.model.product.phone.Phone;
import com.model.product.specifications.ProductType;
import com.model.product.tablet.Tablet;
import com.service.product.laptop.LaptopService;
import com.service.product.phone.PhoneService;
import com.service.product.tablet.TabletService;

import java.util.Random;

public class ProductFactory {
    private static final Random RANDOM = new Random();
    private static final AbstractProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final AbstractProductService<Laptop> LAPTOP_SERVICE = LaptopService.getInstance();
    private static final AbstractProductService<Tablet> TABLET_SERVICE = TabletService.getInstance();


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