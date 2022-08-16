package com.service.product;

import com.model.product.laptop.Laptop;
import com.model.product.laptop.specifications.LaptopManufacturer;
import com.model.product.phone.Phone;
import com.model.product.phone.specifications.PhoneManufacturer;
import com.model.product.Product;
import com.model.product.specifications.ProductType;
import com.model.product.tablet.Tablet;
import com.model.product.tablet.specifications.TabletManufacturer;
import com.repository.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class ProductService<T extends Product> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository<T> repository;

    protected ProductService(ProductRepository<T> repository) {
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

    public void outputProductsWithAPriceHihgerThanTheArgument(double referPrice) {
        repository.getAll()
                .stream().filter(product -> product.getPrice() > referPrice)
                .forEach(System.out::println);
    }

    public int countAllProductsPrice() {
        return repository.getAll()
                .stream()
                .map(Product::getCount)
                .reduce(0, Integer::sum);
    }

    public Map<String, String> sortedByTitleAndConvertToMap() {
        return repository.getAll()
                .stream()
                .sorted(Comparator.comparing(Product::getTitle))
                .distinct()
                .collect(Collectors.toMap
                        (Product::getId,
                                product -> product.getClass().getSimpleName(),
                                (oldProduct, newProduct) -> newProduct
                        )
                );
    }

    public DoubleSummaryStatistics getPriceSummaryStatistic() {
        return repository.getAll()
                .stream()
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
    }

    public Predicate<Collection<T>> availabilityOfPricesForAllCollection =
            (products) -> products.stream().noneMatch(product -> product.getPrice() <= 0);

    public Product mapToProduct(Map<String, Object> fields) {
        final String defaultTitle = "Default Title";
        final Integer defaultCount = 0;
        final Double defaultPrice = 0D;
        final String defaultModel = "Model-1";
        Function<Map<String, Object>, Product> mapToProduct = (map) -> {
            Object productType = map.get("productType");
            if (productType instanceof ProductType type) {
                return switch (type) {
                    case PHONE -> new Phone(
                            (String) map.getOrDefault("title", defaultTitle),
                            (Integer) map.getOrDefault("count", defaultCount),
                            (Double) map.getOrDefault("price", defaultPrice),
                            (String) map.getOrDefault("model", defaultModel),
                            PhoneManufacturer.valueOf(map.getOrDefault("manufacturer", PhoneManufacturer.APPLE).toString()));
                    case LAPTOP -> new Laptop(
                            (String) map.getOrDefault("title", defaultTitle),
                            (Integer) map.getOrDefault("count", defaultCount),
                            (Double) map.getOrDefault("price", defaultPrice),
                            (String) map.getOrDefault("model", defaultModel),
                            LaptopManufacturer.valueOf(map.getOrDefault("manufacturer", LaptopManufacturer.ASUS).toString()));
                    case TABLET -> new Tablet(
                            (String) map.getOrDefault("title", defaultTitle),
                            (Integer) map.getOrDefault("count", defaultCount),
                            (Double) map.getOrDefault("price", defaultPrice),
                            (String) map.getOrDefault("model", defaultModel),
                            TabletManufacturer.valueOf(map.getOrDefault("manufacturer", TabletManufacturer.GOOGLE).toString()));
                };
            } else {
                throw new IllegalArgumentException();
            }
        };
        return mapToProduct.apply(fields);
    }
}
