package com.service;

import com.model.Laptop;
import com.model.LaptopManufacturer;
import com.repository.LaptopRepository;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LaptopService {
    private static final Random RANDOM = new Random();
    private static final LaptopRepository REPOSITORY = new LaptopRepository();

    public void createAndSaveLaptops (int count) {
        List<Laptop> laptopsList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            laptopsList.add(createLaptop());
        }
        REPOSITORY.saveAll(laptopsList);
    }
    private LaptopManufacturer getRandomManufacturer() {
        final LaptopManufacturer[] values = LaptopManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Laptop laptops : REPOSITORY.getAll()) {
            System.out.println(laptops);
        }
    }

    public List<Laptop> getFullList() {
        return REPOSITORY.getAll();
    }

    public boolean delete(String id) {
        return REPOSITORY.delete(id);
    }

    public boolean update(Laptop laptop) {
        return REPOSITORY.update(laptop);
    }

    public Laptop createLaptop() {
        return new Laptop("Title-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                BigDecimal.valueOf(RANDOM.nextDouble(10000.0)).setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue(),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer());
    }
}
