package com.example.service;

import com.example.model.Product;
import com.example.repository.IProductRepository;
import com.example.repository.ProductRepository;

public class ProductService extends AbstractProductService<Product> {
    private static ProductService instance;

    public ProductService(ProductRepository repository) {
        super(repository);
    }

    public static ProductService getInstance() {
        if (instance != null) {
            instance = new ProductService(ProductRepository.getInstance());
        }
        return instance;
    }
}
