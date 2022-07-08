package repository;

import com.model.Laptop;
import com.model.LaptopManufacturer;
import com.repository.LaptopRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LaptopRepositoryTest {

    private LaptopRepository target;

    private Laptop laptop;

    @BeforeEach
    void setUp() {
        final Random random = new Random();
        target = new LaptopRepository();
        laptop = new Laptop(
                "Title-" + random.nextInt(1000),
                random.nextInt(500),
                random.nextDouble(1000.0),
                "Model-" + random.nextInt(10),
                LaptopManufacturer.LENOVO
        );
    }

    @Test
    void save() {
        target.save(laptop);
        final List<Laptop> laptops = target.getAll();
        Assertions.assertEquals(1, laptops.size());
        Assertions.assertEquals(laptops.get(0).getId(), laptop.getId());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void saveAll_singleLaptop() {
        target.saveAll(Collections.singletonList(laptop));
        final List<Laptop> laptops = target.getAll();
        Assertions.assertEquals(1, laptops.size());
        Assertions.assertEquals(laptops.get(0).getId(), laptop.getId());
    }

    @Test
    void saveAll_noLaptop() {
        target.saveAll(Collections.emptyList());
        final List<Laptop> laptops = target.getAll();
        Assertions.assertEquals(0, laptops.size());
    }

    @Test
    void saveAll_manyLaptops() {
        final Laptop otherLaptop = new Laptop("Title", 500, 1000.0, "Model", LaptopManufacturer.LENOVO);
        target.saveAll(List.of(laptop, otherLaptop));
        final List<Laptop> laptops = target.getAll();
        Assertions.assertEquals(2, laptops.size());
        Assertions.assertEquals(laptops.get(0).getId(), laptop.getId());
        Assertions.assertEquals(laptops.get(1).getId(), otherLaptop.getId());
    }

    @Test
    void saveAll_hasDuplicates() {
        final List<Laptop> laptops = new ArrayList<>();
        laptops.add(laptop);
        laptops.add(laptop);
        Assertions.assertThrows(IllegalArgumentException.class, () ->target.saveAll(laptops));
    }

    @Test
    void saveAll_hasNull() {
        final List<Laptop> laptops = new ArrayList<>();
        laptops.add(laptop);
        laptops.add(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(laptops));
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void update() {
        final String newTitle = "New title";
        target.save(laptop);
        laptop.setTitle(newTitle);

        final boolean result = target.update(laptop);

        Assertions.assertTrue(result);
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(newTitle, actualResult.get(0).getTitle());
        Assertions.assertEquals(laptop.getId(), actualResult.get(0).getId());
        Assertions.assertEquals(laptop.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void update_noLaptop() {
        target.save(laptop);
        final Laptop noLaptop = new Laptop("Title", 500, 1000.0, "Model", LaptopManufacturer.LENOVO);
        final boolean result = target.update(noLaptop);

        Assertions.assertFalse(result);
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(laptop.getId(), actualResult.get(0).getId());
        Assertions.assertEquals(laptop.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void delete() {
        target.save(laptop);
        final boolean result = target.delete(laptop.getId());
        Assertions.assertTrue(result);
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void delete_noLaptop() {
        target.save(laptop);
        final Laptop noLaptop = new Laptop("Title", 500, 1000.0, "Model", LaptopManufacturer.LENOVO);
        final boolean result = target.delete(noLaptop.getId());
        Assertions.assertFalse(result);
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll() {
        target.save(laptop);
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll_noLaptops() {
        final List<Laptop> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void findById() {
        target.save(laptop);
        final Optional<Laptop> optionalLaptop = target.findById(laptop.getId());
        Assertions.assertTrue(optionalLaptop.isPresent());
        final Laptop actualLaptop = optionalLaptop.get();
        Assertions.assertEquals(laptop.getId(),actualLaptop.getId());
    }
}
