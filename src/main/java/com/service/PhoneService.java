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

    public PhoneService(PhoneRepository repository) {
        super(repository);
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

    public void deleteIfPresent(String id) {
            repository.findById(id).ifPresent(phone -> repository.delete(id));
    }

    public void updateIfPresentOrElseSaveNew (Phone phone) {
        repository.findById(phone.getId()).ifPresentOrElse(
                updateLaptop -> repository.update(phone),
                () -> repository.save(phone));
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

    public Optional<Phone> findByIdOrGetAny (Phone phone) {
        return repository.findById(phone.getId()).or(() -> repository.getAll().stream().findAny());
    }

    public String mapFromPhoneToString (Phone phone) {
        return repository.findById(phone.getId()).map(Phone::toString).orElse("Not found" + " " + phone.getId());
    }
}
