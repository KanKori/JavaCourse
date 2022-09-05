package com.repository.product.hibernate.laptop;

import com.model.product.laptop.Laptop;

import java.util.List;
import java.util.Optional;

public interface ILaptopRepositoryHibernate {
    void save(Laptop laptop);

    void saveAll(List<Laptop> phones);

    List<Laptop> findAll();

    Optional<Laptop> findById(String id);

    boolean update(Laptop laptop);

    boolean delete(String id);
}
