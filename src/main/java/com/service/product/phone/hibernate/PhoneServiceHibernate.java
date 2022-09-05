package com.service.product.phone.hibernate;

import com.annotations.Autowired;
import com.annotations.Singleton;
import com.model.product.phone.Phone;
import com.model.product.phone.specifications.PhoneManufacturer;
import com.repository.product.hibernate.phone.PhoneRepositoryHibernate;
import com.service.product.phone.PhoneService;

import java.util.Random;

@Singleton
public class PhoneServiceHibernate extends PhoneService {
    private static final Random RANDOM = new Random();
    private static PhoneServiceHibernate instance;

    @Autowired
    public PhoneServiceHibernate(PhoneRepositoryHibernate repository) {
        super(repository);
    }

    public static PhoneServiceHibernate getInstance() {
        if (instance == null) {
            instance = new PhoneServiceHibernate(PhoneRepositoryHibernate.getInstance());
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
