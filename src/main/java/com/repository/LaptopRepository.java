package com.repository;

import com.model.Laptop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LaptopRepository implements CrudRepository<Laptop> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LaptopRepository.class);
    private static final Random RANDOM = new Random();

    private final List<Laptop> laptops;

    public LaptopRepository() {
        laptops = new LinkedList<>();
    }

    public void save(Laptop laptop) {
        if (laptop == null) {
            throw new IllegalArgumentException("Cannot save a null laptop");
        } else {
            checkDuplicates(laptop);
        }
        laptops.add(laptop);
    }

    private void checkDuplicates(Laptop laptop) {
        for (Laptop l: laptops) {
            if (laptop.hashCode() == l.hashCode() && laptop.equals(l)) {
                final IllegalArgumentException exception = new IllegalArgumentException("Duplicate laptop: " + laptop.getId());
                LOGGER.error(exception.getMessage(), exception);
                throw exception;
            }
        }
    }

    public void saveAll(List<Laptop> laptops) {
        for (Laptop laptop : laptops) {
            save(laptop);
        }
    }

    @Override
    public boolean update(Laptop laptop) {
        final Optional<Laptop> result = findById(laptop.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Laptop originLaptop = result.get();
        LaptopCopy.copy(laptop, originLaptop);
        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Laptop> iterator = laptops.iterator();
        while (iterator.hasNext()) {
            final Laptop laptop = iterator.next();
            if (laptop.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Laptop> getAll() {
        if (laptops.isEmpty()) {
            return Collections.emptyList();
        }
        return laptops;
    }

    public Laptop getRandomLaptop() {
        return laptops.get(RANDOM.nextInt(laptops.size()));
    }

    @Override
    public Optional<Laptop> findById(String id) {
        Laptop result = null;
        for (Laptop laptop : laptops) {
            if (laptop.getId().equals(id)) {
                result = laptop;
            }
        }
        return Optional.ofNullable(result);
    }

    private static class LaptopCopy {
        private static void copy(final Laptop from, final Laptop to) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
            to.setTitle(from.getTitle());
        }
    }
}
