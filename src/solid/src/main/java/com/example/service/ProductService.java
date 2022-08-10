package com.example.service;

import com.example.model.Product;
import com.example.repository.IProductRepository;
import com.example.repository.ProductRepository;

public class ProductService extends AbstractProductService<Product> {
    public ProductService(ProductRepository repository) {
        super(repository);
    }
}
