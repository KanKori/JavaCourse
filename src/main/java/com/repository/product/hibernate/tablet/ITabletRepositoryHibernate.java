package com.repository.product.hibernate.tablet;

import com.model.product.tablet.Tablet;

import java.util.List;
import java.util.Optional;

public interface ITabletRepositoryHibernate {
    void save(Tablet tablet);

    void saveAll(List<Tablet> tablets);

    List<Tablet> findAll();

    Optional<Tablet> findById(String id);

    boolean update(Tablet tablet);

    boolean delete(String id);
}
