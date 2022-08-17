package com.service.product.tablet.database;

import com.model.product.tablet.Tablet;
import com.model.product.tablet.specifications.TabletManufacturer;
import com.repository.product.database.tablet.TabletRepositoryDBI;
import com.repository.product.tablet.TabletRepositoryI;
import com.service.product.AbstractProductService;
import com.service.product.tablet.TabletServiceAbstract;

import java.util.Random;

public class TabletServiceDBAbstract extends AbstractProductService<Tablet> {
    private static final Random RANDOM = new Random();
    private static TabletServiceAbstract instance;

    public TabletServiceDBAbstract(TabletRepositoryDBI repository) {
        super(repository);
    }

    public static TabletServiceAbstract getInstance() {
        if (instance == null) {
            instance = new TabletServiceAbstract(TabletRepositoryI.getInstance());
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