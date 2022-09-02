package com.service.product.phone.mongo;

import com.model.product.phone.Phone;
import com.model.product.phone.specifications.PhoneManufacturer;
import com.repository.product.mongo.phone.PhoneRepositoryMongo;
import com.service.product.phone.PhoneService;

import java.util.Random;

public class PhoneServiceMongo extends PhoneService {
    private static final Random RANDOM = new Random();
    private static PhoneServiceMongo instance;

    public PhoneServiceMongo(PhoneRepositoryMongo repository) {
        super(repository);
    }

    public static PhoneServiceMongo getInstance() {
        if (instance == null) {
            instance = new PhoneServiceMongo(PhoneRepositoryMongo.getInstance());
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
