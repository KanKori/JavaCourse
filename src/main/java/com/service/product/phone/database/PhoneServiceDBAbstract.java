package com.service.product.phone.database;

import com.model.product.phone.Phone;
import com.model.product.phone.specifications.PhoneManufacturer;
import com.repository.product.database.phone.PhoneRepositoryDBI;
import com.service.product.AbstractProductService;

import java.util.Random;

public class PhoneServiceDBAbstract extends AbstractProductService<Phone> {
    private static final Random RANDOM = new Random();
    private static PhoneServiceDBAbstract instance;

    public PhoneServiceDBAbstract(PhoneRepositoryDBI repository) {
        super(repository);
    }

    public static PhoneServiceDBAbstract getInstance() {
        if (instance == null) {
            instance = new PhoneServiceDBAbstract(PhoneRepositoryDBI.getInstance());
        }
        return instance;
    }

    public Phone createProduct() {
        return new Phone(
                "Title-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer());
    }

    private PhoneManufacturer getRandomManufacturer() {
        final PhoneManufacturer[] values = PhoneManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
