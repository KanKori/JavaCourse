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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Test
    public void deleteIfPresent() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        when(repository.findById(anyString())).thenReturn(Optional.of(laptop));
        target.deleteIfPresent(laptop.getId());
        verify(repository).delete(laptop.getId());
    }

    @Test
    public void doNotDeleteIfMissing() {
        final String ghostId = target.createProduct().getId();
        target.deleteIfPresent(ghostId);
        verify(repository, times(0)).delete(ghostId);
    }

    @Test
    public void updateIfPresent() {
        final Laptop laptop = target.createProduct();
        when(repository.findById(laptop.getId())).thenReturn(Optional.of(laptop));
        target.updateIfPresentOrElseSaveNew(laptop);
        verify(repository).update(laptop);
    }

    @Test
    public void updateOrElseSaveNew() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        target.updateIfPresentOrElseSaveNew(laptop);
        verify(repository).save(laptop);
    }

    @Test
    public void findByIdOrElseRandom() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        target.findByIdOrElseRandom(laptop.getId());
        verify(repository).getRandomProduct();
    }

    @Test
    public void findByIdOrElseGetRandom() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        target.findByIdOrElseGetRandom(laptop.getId());
        verify(repository).getRandomProduct();
    }

    @Test
    public void findByIdOrElseThrow() {
        String incorrectId = "x";
        when(repository.findById(incorrectId)).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findByIdOrElseThrow("incorrectId"));
    }

    @Test
    public void deleteLaptopFindByIdIfManufacturerLenovo() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        when(repository.findById(anyString())).thenReturn(Optional.of(laptop));
        target.deleteLaptopFindByIdIfManufacturerLenovo(laptop.getId());
        verify(repository).delete(laptop.getId());
    }

    @Test
    public void deleteLaptopFindByIdIfManufacturerLenovo_notLenovo() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        target.deleteLaptopFindByIdIfManufacturerLenovo(laptop.getId());
        verify(repository, times(0)).delete(laptop.getId());
    }

    @Test
    public void findByIdOrGetAny() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        when(repository.findById(anyString())).thenReturn(Optional.of(laptop));
        target.findByIdOrGetAny(laptop);
        assertEquals(target.findByIdOrGetAny(laptop), Optional.of(laptop));
    }

    @Test
    public void mapFromLaptopToString() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        when(repository.findById(anyString())).thenReturn(Optional.of(laptop));
        assertEquals(target.mapFromProductToString(laptop), laptop.toString());
    }

    @Test
    public void mapFromLaptopToString_null() {
        final Laptop laptop = new Laptop("Title", 100, 1000.0, "Model", LaptopManufacturer.LENOVO);
        assertNotEquals(target.mapFromProductToString(laptop), null);
    }
}
