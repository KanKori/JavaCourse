package com.service.product.tablet.database;

import com.model.product.tablet.Tablet;
import com.model.product.tablet.specifications.TabletManufacturer;
import com.repository.product.database.tablet.TabletRepositoryDB;
import com.repository.product.tablet.TabletRepository;
import com.service.product.AbstractProductService;
import com.service.product.tablet.TabletService;

import java.util.Random;

public class TabletServiceDB extends AbstractProductService<Tablet> {
    private static final Random RANDOM = new Random();
    private static TabletService instance;

    public TabletServiceDB(TabletRepositoryDB repository) {
        super(repository);
    }

    public static TabletService getInstance() {
        if (instance == null) {
            instance = new TabletService(TabletRepository.getInstance());
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