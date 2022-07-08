package com.service;

import com.model.*;

import java.util.Random;

public class ProductFactory {
    private static final Random RANDOM = new Random();

    private ProductFactory() {
    }

    public static Product createProduct(ProductType type) {
        return switch (type) {
            case TABLET -> new Tablet(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomTabletManufacturer()
            );
            case PHONE -> new Phone(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomPhoneManufacturer()
            );
            case LAPTOP -> new Laptop(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomLaptopManufacturer()
            );
            default -> throw new IllegalArgumentException("Unknown Product type: " + type);
        };
    }

    private static LaptopManufacturer getRandomLaptopManufacturer() {
        final LaptopManufacturer[] values = LaptopManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private static PhoneManufacturer getRandomPhoneManufacturer() {
        final PhoneManufacturer[] values = PhoneManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private static TabletManufacturer getRandomTabletManufacturer() {
        final TabletManufacturer[] values = TabletManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }


}