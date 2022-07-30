package com.service;

import com.model.Product;
import com.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    @SuppressWarnings("unchecked")
    public void update(Product product) {
        repository.update((T) product);
    }

    public List<T> getAll() {
        return repository.getAll();
    }

    public void delete(String id) {
        repository.delete(id);
    }

    public void printAll() {
        for (T product : repository.getAll()) {
            System.out.println(product);
        }
    }

    public void deleteIfPresent(String id) {
        repository.findById(id).ifPresent(product -> repository.delete(id));
    }

    public void updateIfPresentOrElseSaveNew(T product) {
        repository.findById(product.getId()).ifPresentOrElse(
                updateLaptop -> repository.update(product),
                () -> repository.save(product));
    }

    public T findByIdOrElseRandom(String id) {
        return repository.findById(id).orElse(repository.getRandomProduct());
    }

    public T findByIdOrElseGetRandom(String id) {
        return repository.findById(id).orElseGet(repository::getRandomProduct);
    }

    public T findByIdOrElseThrow(String id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Optional<T> findByIdOrGetAny(T product) {
        return repository.findById(product.getId()).or(() -> repository.getAll().stream().findAny());
    }

    public String mapFromProductToString(T product) {
        return repository.findById(product.getId()).map(T::toString).orElse("Not found" + " " + product.getId());
    }
}
