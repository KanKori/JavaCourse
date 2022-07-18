package com.service;

import com.model.PhoneManufacturer;
import com.model.Phone;
import com.repository.PhoneRepository;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PhoneService {
    private static final Random RANDOM = new Random();
    private static final PhoneRepository REPOSITORY = new PhoneRepository();

    public void createAndSavePhones(int count) {
        List<Phone> phones = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            phones.add(createPhone());
        }
        REPOSITORY.saveAll(phones);
    }

    private PhoneManufacturer getRandomManufacturer() {
        final PhoneManufacturer[] values = PhoneManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (Phone phone : REPOSITORY.getAll()) {
            System.out.println(phone); // TODO: 02/07/22  
        }
    }

    public List<Phone> getFullList() {
        return REPOSITORY.getAll();
    }

    public boolean delete(String id) {
        return REPOSITORY.delete(id);
    }

    public boolean update(Phone phone) {
        return REPOSITORY.update(phone);
    }

    public Phone createPhone() {
        return new Phone("Title-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(10000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer());
    }
}
