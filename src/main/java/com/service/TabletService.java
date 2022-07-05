package com.service;

import com.model.Tablet;
import com.model.TabletManufacturer;
import com.repository.TabletRepository;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TabletService {
    private static final Random RANDOM = new Random();
    private static final TabletRepository REPOSITORY = new TabletRepository();

    public void createAndSaveTablets (int count) {
        List<Tablet> tabletList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            tabletList.add(createTablet());
        }
        REPOSITORY.saveAll(tabletList);
    }
    private TabletManufacturer getRandomManufacturer() {
        final TabletManufacturer[] values = TabletManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Tablet tablets : REPOSITORY.getAll()) {
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
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)).setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue(),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer());
    }
}
