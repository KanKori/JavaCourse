package com.service.product.laptop.database;

import com.model.product.laptop.Laptop;
import com.model.product.laptop.specifications.LaptopManufacturer;
import com.repository.product.database.laptop.LaptopRepositoryDBI;
import com.service.product.AbstractProductService;

import java.util.Random;

public class LaptopServiceDBAbstract extends AbstractProductService<Laptop> {
    private static final Random RANDOM = new Random();
    private static LaptopServiceDBAbstract instance;

    public LaptopServiceDBAbstract(LaptopRepositoryDBI repository) {
        super(repository);
    }

    public static LaptopServiceDBAbstract getInstance() {
        if (instance == null) {
            instance = new LaptopServiceDBAbstract(LaptopRepositoryDBI.getInstance());
        }
        return instance;
    }

    public Laptop createProduct() {
        return new Laptop(
                "Title-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer());
    }

    private LaptopManufacturer getRandomManufacturer() {
        final LaptopManufacturer[] values = LaptopManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}