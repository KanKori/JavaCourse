package com.repository;

import com.model.Tablet;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TabletRepository implements CrudRepository<Tablet> {
    private final List<Tablet> tablets;

    public TabletRepository() {
        tablets = new LinkedList<>();
    }

    @Override
    public void save(Tablet tablet) {
        tablets.add(tablet);
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
        if (result.isEmpty())
        return false;
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


    private static class  TabletCopy {
        private static void copy (final Tablet from, final Tablet to) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
            to.setTitle(from.getTitle());
        }
    }
}
