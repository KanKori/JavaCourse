package com.service;

import com.model.PhoneManufacturer;
import com.model.Phone;
import com.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PhoneService extends ProductService<Phone> {
    private static final Random RANDOM = new Random();
    private static PhoneService instance;

    public PhoneService(PhoneRepository repository) {
        super(repository);
    }

    public static PhoneService getInstance() {
        if (instance == null) {
            instance = new PhoneService(PhoneRepository.getInstance());
        }
        return instance;
    }

    public static PhoneService getInstance(final PhoneRepository repository) {
        if (instance == null) {
            instance = new PhoneService(repository);
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

    public void deletePhoneFindByIdIfManufacturerApple(String id) {
        getRepository().findById(id)
                .filter(checkingPhone -> checkingPhone.getPhoneManufacturer().equals(PhoneManufacturer.APPLE))
                .ifPresentOrElse(checkedPhone -> getRepository().delete(checkedPhone.getId()),
                        () -> System.out.println("no one Apple Phone founded"));
    }
}
