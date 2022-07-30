package com.service;

import com.model.Laptop;
import com.model.LaptopManufacturer;
import com.repository.LaptopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LaptopService extends ProductService<Laptop> {
    private static final Random RANDOM = new Random();

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

    public void deleteIfPresent(String id) {
        repository.findById(id).ifPresent(laptop -> repository.delete(id));
    }

    public void updateIfPresentOrElseSaveNew(Laptop laptop) {
        repository.findById(laptop.getId()).ifPresentOrElse(
                updateLaptop -> repository.update(laptop),
                () -> repository.save(laptop));
    }

    public Laptop findByIdOrElseRandom(String id) {
        return repository.findById(id).orElse(repository.getRandomLaptop());
    }

    public Laptop findByIdOrElseGetRandom(String id) {
        return repository.findById(id).orElseGet(repository::getRandomLaptop);
    }

    public Laptop findByIdOrElseThrow(String id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteLaptopFindByIdIfManufacturerLenovo(String id) {
        repository.findById(id)
                .filter(checkingLaptop -> checkingLaptop.getLaptopManufacturer().equals(LaptopManufacturer.LENOVO))
                .ifPresentOrElse(checkedLaptop -> repository.delete(checkedLaptop.getId()),
                        () -> System.out.println("no one Lenovo Laptop founded"));
    }

    public Optional<Laptop> findByIdOrGetAny(Laptop laptop) {
        return repository.findById(laptop.getId()).or(() -> repository.getAll().stream().findAny());
    }

    public String mapFromLaptopToString(Laptop laptop) {
        return repository.findById(laptop.getId()).map(Laptop::toString).orElse("Not found" + " " + laptop.getId());

    }
}
