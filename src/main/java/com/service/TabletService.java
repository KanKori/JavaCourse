package com.service;

import com.model.Tablet;
import com.model.Tablet;
import com.model.Tablet;
import com.model.TabletManufacturer;
import com.repository.TabletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TabletService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TabletService.class);
    private static final Random RANDOM = new Random();
    private final TabletRepository repository;

    public TabletService(TabletRepository repository) {
        this.repository = repository;
    }

    public void createAndSaveTablets (int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count must been bigger then 0");
        }
        List<Tablet> tablets = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Tablet tablet = new Tablet(
                    "Title-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    "Model-" + RANDOM.nextInt(10),
                    getRandomManufacturer()
            );
            tablets.add(tablet);
            LOGGER.info("Tablet {} has been saved", tablet.getId());
        }
        repository.saveAll(tablets);
    }

    public Tablet createTablet() {
        return new Tablet("Title-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(10000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer());
    }

    public void saveTablet(Tablet tablet) {
        if (tablet.getCount() == 0) {
            tablet.setCount(-1);
        }
        repository.save(tablet);
    }

    private TabletManufacturer getRandomManufacturer() {
        final TabletManufacturer[] values = TabletManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public List<Tablet> getAll() {
        return repository.getAll();
    }

    public void printAll() {
        for (Tablet tablets : repository.getAll()) {
            System.out.println(tablets);
        }
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }

    public boolean update(Tablet tablet) {
        return repository.update(tablet);
    }

    public void deleteIfPresent(String id) {
        repository.findById(id).ifPresent(tablet -> repository.delete(id));
    }

    public void updateIfPresentOrElseSaveNew (Tablet tablet) {
        repository.findById(tablet.getId()).ifPresentOrElse(
                updateLaptop -> repository.update(tablet),
                () -> repository.save(tablet));
    }

    public Tablet findByIdOrElseRandom (String id) {
        return repository.findById(id).orElse(repository.getRandomTablet());
    }

    public Tablet findByIdOrElseGetRandom (String id) {
        return repository.findById(id).orElseGet(repository::getRandomTablet);
    }

    public Tablet findByIdOrElseThrow (String id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteTabletFindByIdIfManufacturerGoogle (String id) {
        repository.findById(id)
                .filter(checkingTablet -> checkingTablet.getTabletManufacturer().equals(TabletManufacturer.GOOGLE))
                .ifPresentOrElse(checkedTablet -> repository.delete(checkedTablet.getId()),
                        () -> System.out.println("no one Google tablet founded"));
    }

    public Optional<Tablet> findByIdOrGetAny (Tablet tablet) {
        return repository.findById(tablet.getId()).or(() -> repository.getAll().stream().findAny());
    }

    public String mapFromTabletToString (Tablet tablet) {
        return repository.findById(tablet.getId()).map(Tablet::toString).orElse("Not found" + " " + tablet.getId());

    }
}
