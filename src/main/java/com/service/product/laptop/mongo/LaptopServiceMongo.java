package com.service.product.laptop.mongo;

import com.annotations.Singleton;
import com.model.product.laptop.Laptop;
import com.model.product.laptop.specifications.LaptopManufacturer;
import com.repository.product.mongo.laptop.LaptopRepositoryMongo;
import com.service.product.laptop.LaptopService;

import java.util.Random;

@Singleton
public class LaptopServiceMongo extends LaptopService {
    private static final Random RANDOM = new Random();
    private static LaptopServiceMongo instance;

    public LaptopServiceMongo(LaptopRepositoryMongo repository) {
        super(repository);
    }

    public static LaptopServiceMongo getInstance() {
        if (instance == null) {
            instance = new LaptopServiceMongo(LaptopRepositoryMongo.getInstance());
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
