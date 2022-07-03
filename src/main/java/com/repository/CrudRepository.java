package com.repository;

import com.model.Phone;
import com.model.Product;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    void save(T t);

    void saveAll(List<T> t);

    boolean update(T t);

    boolean delete(String id);

    List<T> getAll();

    Optional<T> findById(String id);
}
