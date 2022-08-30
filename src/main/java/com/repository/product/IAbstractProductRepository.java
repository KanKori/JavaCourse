package com.repository.product;

import com.model.product.AbstractProduct;

import java.util.List;
import java.util.Optional;

public interface IAbstractProductRepository<T extends AbstractProduct> {

    void save(T product);

    void saveAll(List<T> products);

    List<T> getAll();

    Optional<T> findById(String id);

    boolean update(T product);

    boolean delete(String id);

    T getRandomProduct();
}