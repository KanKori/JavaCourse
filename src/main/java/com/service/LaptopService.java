package com.service;

import com.model.Laptop;
import com.model.LaptopManufacturer;
import com.repository.LaptopRepository;
import java.util.Random;

public class LaptopService extends ProductService<Laptop> {
    private static final Random RANDOM = new Random();
    private static final LaptopRepository REPOSITORY = new LaptopRepository();

    public LaptopService(LaptopRepository repository) {
        super(repository);
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

    public void deleteLaptopFindByIdIfManufacturerLenovo(String id) {
        getRepository().findById(id)
                .filter(checkingLaptop -> checkingLaptop.getLaptopManufacturer().equals(LaptopManufacturer.LENOVO))
                .ifPresentOrElse(checkedLaptop -> getRepository().delete(checkedLaptop.getId()),
                        () -> System.out.println("no one Lenovo Laptop founded"));
    }
}
