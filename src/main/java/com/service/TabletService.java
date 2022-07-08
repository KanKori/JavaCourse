package com.service;

import com.model.Tablet;
import com.model.Tablet;
import com.model.TabletManufacturer;
import com.repository.TabletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TabletService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TabletService.class);
    private static final Random RANDOM = new Random();
    private static final TabletRepository REPOSITORY = new TabletRepository();
    private final TabletRepository repository;

    public TabletService(TabletRepository repository) {
        this.repository = repository;
    }

    public void createAndSaveTablets (int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count must been bigger then 0");
        }
        List<Tablet> tablets = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Tablet tablet = new Tablet(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer()
            );
            tablets.add(tablet);
            LOGGER.info("Tablet {} has been saved", tablet.getId());
        }
        repository.saveAll(tablets);
    }

    public void saveTablet(Tablet tablet) {
        if (tablet.getCount() == 0) {
            tablet.setCount(-1);
        }
        repository.save(tablet);
    }

    private TabletManufacturer getRandomManufacturer() {
        final TabletManufacturer[] values = TabletManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public List<Tablet> getAll() {
        return repository.getAll();
    }

    public void printAll() {
        for (Tablet tablets : repository.getAll()) {
            System.out.println(tablets);
        }
    }

    public List<Tablet> getFullList() {
        return REPOSITORY.getAll();
    }

    public boolean delete(String id) {
        return REPOSITORY.delete(id);
    }

    public boolean update(Tablet tablet) {
        return REPOSITORY.update(tablet);
    }

    public Tablet createTablet() {
        return new Tablet("Title-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(10000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer());
    }
}
