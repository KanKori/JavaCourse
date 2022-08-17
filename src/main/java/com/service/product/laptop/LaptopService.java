package com.service.product.laptop;

import com.model.product.laptop.Laptop;
import com.model.product.laptop.specifications.LaptopManufacturer;
import com.repository.product.laptop.LaptopRepository;
import com.service.product.AbstractProductService;

import java.util.Random;

public class LaptopService extends AbstractProductService<Laptop> {
    private static final Random RANDOM = new Random();
    private static LaptopService instance;

    public LaptopService(LaptopRepository repository) {
        super(repository);
    }

    public static LaptopService getInstance() {
        if (instance == null) {
            instance = new LaptopService(LaptopRepository.getInstance());
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

    public void deleteLaptopFindByIdIfManufacturerLenovo(String id) {
        getRepository().findById(id)
                .filter(checkingLaptop -> checkingLaptop.getLaptopManufacturer().equals(LaptopManufacturer.LENOVO))
                .ifPresentOrElse(checkedLaptop -> getRepository().delete(checkedLaptop.getId()),
                        () -> System.out.println("no one Lenovo Laptop founded"));
    }
}
