package com.service.product.phone;

import com.model.operating_system.OperatingSystem;
import com.model.product.phone.specifications.PhoneManufacturer;
import com.model.product.phone.Phone;
import com.repository.product.phone.PhoneRepository;
import com.service.product.AbstractProductService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

public class PhoneService extends AbstractProductService<Phone> {
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

    public static Phone createPhoneFromMap(Map<String, String> phoneMap) {
        return new Phone(
                phoneMap.getOrDefault("title", "DefaultTitle"),
                phoneMap.getOrDefault("model", "DefaultModel"),
                Double.parseDouble(phoneMap.getOrDefault("price", String.valueOf(0))),
                phoneMap.get("currency"),
                PhoneManufacturer.valueOf(phoneMap.getOrDefault("manufacturer", PhoneManufacturer.SAMSUNG.name())),
                LocalDateTime.parse(phoneMap.get("created"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")),
                Integer.parseInt(phoneMap.get("count")),
                new OperatingSystem(phoneMap.get("designation"), Integer.parseInt(phoneMap.get("version")))
        );
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
