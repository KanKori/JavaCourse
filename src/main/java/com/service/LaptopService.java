package com.service;

import com.model.Laptop;
import com.model.LaptopManufacturer;
import com.repository.LaptopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LaptopService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LaptopService.class);
    private static final Random RANDOM = new Random();
    private final LaptopRepository repository;

    public LaptopService(LaptopRepository repository) {
        this.repository = repository;
    }

    public void createAndSaveLaptops (int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count must been bigger then 0");
        }
        List<Laptop> laptops = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Laptop laptop = new Laptop(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer()
            );
            laptops.add(laptop);
            LOGGER.info("Laptop {} has been saved", laptop.getId());
        }
        repository.saveAll(laptops);
    }

    public Laptop createLaptop() {
        return new Laptop("Title-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(10000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer());
    }
    public void saveLaptop(Laptop laptop) {
        if (laptop.getCount() == 0) {
            laptop.setCount(-1);
        }
        repository.save(laptop);
    }

    private LaptopManufacturer getRandomManufacturer() {
        final LaptopManufacturer[] values = LaptopManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public List<Laptop> getAll() {
        return repository.getAll();
    }

    public void printAll() {
        for (Laptop laptops : repository.getAll()) {
            System.out.println(laptops);
        }
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }

    public boolean update(Laptop laptop) {
        return repository.update(laptop);
    }

    public void deleteIfPresent(String id) {
        repository.findById(id).ifPresent(laptop -> repository.delete(id));
    }

    public void updateIfPresentOrElseSaveNew (Laptop laptop) {
        repository.findById(laptop.getId()).ifPresentOrElse(
                updateLaptop -> repository.update(laptop),
                () -> saveLaptop(laptop));
    }

    public Laptop findByIdOrGetRandom (String id) {
        return repository.findById(id).orElse(repository.getRandomLaptop());
    }
}
