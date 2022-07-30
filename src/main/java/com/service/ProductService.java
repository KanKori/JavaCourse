package com.service;

import com.model.Product;
import com.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public abstract class ProductService<T extends Product> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);
    private final ProductRepository<T> repository;

    public ProductService(ProductRepository<T> repository) {
        this.repository = repository;
    }

    public void createAndSaveProducts(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count must been bigger then 0");
        }
        List<T> products = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T product = createProduct();
            products.add(product);
            LOGGER.info(product.getClass() + " {} has been saved", product.getId());
        }
        repository.saveAll(products);
    }

    protected abstract T createProduct();

    public void save(T product) {
        if (product.getCount() == 0) {
            product.setCount(-1);
        }
        repository.save(product);
    }

    protected ProductRepository<T> getRepository() {
        return repository;
    }

    public boolean update(T product) {
        return repository.update(product);
    }

    public List<T> getAll() {
        return repository.getAll();
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }

    public void printAll() {
        for (T product : repository.getAll()) {
            System.out.println(product);
        }
    }
}
