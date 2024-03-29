package com.repository.product.tablet;

import com.annotations.Autowired;
import com.annotations.Singleton;
import com.model.product.tablet.Tablet;
import com.repository.product.IAbstractProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Singleton
public class TabletRepository implements IAbstractProductRepository<Tablet> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TabletRepository.class);
    private static final Random RANDOM = new Random();
    private final List<Tablet> tablets;
    private static TabletRepository instance;

    @Autowired
    public TabletRepository() {
        tablets = new LinkedList<>();
    }

    public static TabletRepository getInstance() {
        if (instance == null) {
            instance = new TabletRepository();
        }
        return instance;
    }

    @Override
    public void save(Tablet tablet) {
        if (tablet == null) {
            throw new IllegalArgumentException("Cannot save a null tablet");
        } else {
            checkDuplicates(tablet);
        }
        tablets.add(tablet);
    }

    private void checkDuplicates(Tablet tablet) {
        for (Tablet t: tablets) {
            if (tablet.hashCode() == t.hashCode() && tablet.equals(t)) {
                final IllegalArgumentException exception = new IllegalArgumentException("Duplicate phone: " + tablet.getId());
                LOGGER.error(exception.getMessage(), exception);
                throw exception;
            }
        }
    }

    @Override
    public void saveAll(List<Tablet> tablets) {
        for (Tablet tablet : tablets) {
            save(tablet);
        }
    }

    @Override
    public boolean update(Tablet tablet) {
        final Optional<Tablet> result = findById(tablet.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Tablet originTablet = result.get();
        TabletCopy.copy(tablet, originTablet);
        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Tablet> iterator = tablets.iterator();
        while (iterator.hasNext()) {
            final Tablet tablet = iterator.next();
            if (tablet.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Tablet> getAll() {
        if (tablets.isEmpty()) {
            return Collections.emptyList();
        }
        return tablets;
    }

    public Tablet getRandomProduct() {
        return tablets.get(RANDOM.nextInt(tablets.size()));
    }

    @Override
    public Optional<Tablet> findById(String id) {
        Tablet result = null;
        for (Tablet tablet : tablets) {
            if (tablet.getId().equals(id)) {
                result = tablet;
            }
        }
        return Optional.ofNullable(result);
    }


    private static class TabletCopy {
        private static void copy(final Tablet from, final Tablet to) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
            to.setTitle(from.getTitle());
        }
    }
}
