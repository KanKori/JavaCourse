package com;

import com.model.Laptop;
import com.model.ProductComparator;
import com.repository.LaptopRepository;
import com.service.LaptopService;

import java.util.ArrayList;
import java.util.List;


public class Main {
    private static final LaptopService LAPTOP_SERVICE = new LaptopService(new LaptopRepository());
    public static void main(String[] args) {
        LAPTOP_SERVICE.createAndSaveProducts(4);
        List<Laptop> laptops = new ArrayList<>(LAPTOP_SERVICE.getAll());
        laptops.sort(new ProductComparator<>());
        laptops.forEach(System.out::println);
    }
}
