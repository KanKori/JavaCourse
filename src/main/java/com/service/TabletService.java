package com.service;

import com.model.Tablet;
import com.model.TabletManufacturer;
import com.repository.TabletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TabletService extends ProductService<Tablet> {
    private static final Random RANDOM = new Random();

    public TabletService(TabletRepository repository) {
        super(repository);
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

    public void deleteTabletFindByIdIfManufacturerGoogle (String id) {
        getRepository().findById(id)
                .filter(checkingTablet -> checkingTablet.getTabletManufacturer().equals(TabletManufacturer.GOOGLE))
                .ifPresentOrElse(checkedTablet -> getRepository().delete(checkedTablet.getId()),
                        () -> System.out.println("no one Google tablet founded"));
    }
}
