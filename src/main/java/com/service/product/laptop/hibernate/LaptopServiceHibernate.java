package com.service.product.laptop.hibernate;

import com.annotations.Autowired;
import com.annotations.Singleton;
import com.model.product.laptop.Laptop;
import com.model.product.laptop.specifications.LaptopManufacturer;
import com.repository.product.hibernate.laptop.LaptopRepositoryHibernate;
import com.service.product.laptop.LaptopService;

import java.util.Random;

@Singleton
public class LaptopServiceHibernate extends LaptopService {
    private static final Random RANDOM = new Random();
    private static LaptopServiceHibernate instance;

    @Autowired
    public LaptopServiceHibernate(LaptopRepositoryHibernate repository) {
        super(repository);
    }

    public static LaptopServiceHibernate getInstance() {
        if (instance == null) {
            instance = new LaptopServiceHibernate(LaptopRepositoryHibernate.getInstance());
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