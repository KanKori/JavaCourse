package com.service.product.tablet.mongo;

import com.model.product.tablet.Tablet;
import com.model.product.tablet.specifications.TabletManufacturer;
import com.repository.product.mongo.tablet.TabletRepositoryMongo;
import com.service.product.tablet.TabletService;

import java.util.Random;

public class TabletServiceMongo extends TabletService {
    private static final Random RANDOM = new Random();
    private static TabletServiceMongo instance;

    public TabletServiceMongo(TabletRepositoryMongo repository) {
        super(repository);
    }

    public static TabletServiceMongo getInstance() {
        if (instance == null) {
            instance = new TabletServiceMongo(TabletRepositoryMongo.getInstance());
        }
        return instance;
    }

    public Tablet createProduct() {
        return new Tablet(
                "Title-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer());
    }

    private TabletManufacturer getRandomManufacturer() {
        final TabletManufacturer[] values = TabletManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
