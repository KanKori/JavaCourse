package service;

import com.model.Laptop;
import com.model.LaptopManufacturer;
import com.repository.LaptopRepository;
import com.service.LaptopService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LaptopServiceTest {

    private LaptopService target;
    private LaptopRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(LaptopRepository.class);
        target = new LaptopService(repository);
    }

    @Test
    void createAndSaveLaptops_negativeCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(-1));
    }

    @Test
    void createAndSaveLaptops_zeroCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(0));
    }

    @Test
    void createAndSaveLaptops() {
        target.createAndSaveProducts(2);
        Mockito.verify(repository).saveAll(Mockito.anyList());
    }

    @Test
    void getAll() {
        target.getAll();
        Mockito.verify(repository).getAll();
    }

    @Test
    void printAll() {
        target.printAll();
        Mockito.verify(repository).getAll();
    }

    @Test
    void saveLaptop() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        target.save(laptop);

        ArgumentCaptor<Laptop> argument = ArgumentCaptor.forClass(Laptop.class);
        Mockito.verify(repository).save(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
    }

    @Test
    void saveLaptop_zeroCount() {
        final Laptop laptop = new Laptop("Title", 0, 1000.0, "Model", LaptopManufacturer.LENOVO);
        target.save(laptop);

        ArgumentCaptor<Laptop> argument = ArgumentCaptor.forClass(Laptop.class);
        Mockito.verify(repository).save(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
        Assertions.assertEquals(-1, argument.getValue().getCount());
    }

    @Test
    public void saveLaptop_verifyTimes() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        target.save(laptop);
        ArgumentCaptor<Laptop> laptopArgumentCaptor = ArgumentCaptor.forClass(Laptop.class);
        verify(repository, times(1)).save(laptopArgumentCaptor.capture());
        Assertions.assertEquals("Title", laptopArgumentCaptor.getValue().getTitle());
    }

    @Test
    public void deleteLaptop() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        target.delete(laptop.getId());
        verify(repository).delete(laptop.getId());
    }

    @Test
    public void updateLaptop() {
        final Laptop laptop = target.createProduct();
        when(repository.findById("")).thenReturn(Optional.of(laptop));
        target.update(laptop);
        verify(repository).update(laptop);
    }
}
