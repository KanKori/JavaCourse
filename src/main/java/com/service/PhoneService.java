package com.service;

import com.model.PhoneManufacturer;
import com.model.Phone;
import com.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PhoneService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);
    private static final Random RANDOM = new Random();
    private final PhoneRepository repository;

    public PhoneService(PhoneRepository repository) {
        this.repository = repository;
    }

    public void createAndSavePhones(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count must been bigger then 0");
        }
        List<Phone> phones = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Phone phone = new Phone(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer()
            );
            phones.add(phone);
            LOGGER.info("Phone {} has been saved", phone.getId());
        }
        repository.saveAll(phones);
    }

    public Phone createPhone() {
        return new Phone("Title-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(10000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer());
    }
    public void savePhone(Phone phone) {
        if (phone.getCount() == 0) {
            phone.setCount(-1);
        }
        repository.save(phone);
    }

    private PhoneManufacturer getRandomManufacturer() {
        final PhoneManufacturer[] values = PhoneManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public List<Phone> getAll() {
        return repository.getAll();
    }

    public void printAll() {
        for (Phone phone : repository.getAll()) {
            System.out.println(phone);
        }
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }

    public boolean update(Phone phone) {
        return repository.update(phone);
    }

    public void deleteIfPresent(String id) {
            repository.findById(id).ifPresent(phone -> repository.delete(id));
    }

    public void updateIfPresentOrElseSaveNew (Phone phone) {
        repository.findById(phone.getId()).ifPresentOrElse(
                updateLaptop -> repository.update(phone),
                () -> savePhone(phone));
    }

    public Phone findByIdOrElseRandom (String id) {
        return repository.findById(id).orElse(repository.getRandomPhone());
    }

    public Phone findByIdOrElseGetRandom (String id) {
        return repository.findById(id).orElseGet(repository::getRandomPhone);
    }

    public Phone findByIdOrElseThrow (String id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void deletePhoneFindByIdIfManufacturerApple (String id) {
        repository.findById(id)
                .filter(checkingPhone -> checkingPhone.getPhoneManufacturer().equals(PhoneManufacturer.APPLE))
                .ifPresentOrElse(checkedPhone -> repository.delete(checkedPhone.getId()),
                        () -> System.out.println("no one Apple Phone founded"));
    }
}
