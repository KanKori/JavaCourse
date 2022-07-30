package service;

import com.model.PhoneManufacturer;
import com.model.Phone;
import com.repository.PhoneRepository;
import com.service.PhoneService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PhoneServiceTest {

    private PhoneService target;
    private PhoneRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PhoneRepository.class);
        target = new PhoneService(repository);
    }

    @Test
    void createAndSavePhones_negativeCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(-1));
    }

    @Test
    void createAndSavePhones_zeroCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(0));
    }

    @Test
    void createAndSavePhones() {
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
    void savePhone() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        target.save(phone);

        ArgumentCaptor<Phone> argument = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(repository).save(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
    }

    @Test
    void savePhone_zeroCount() {
        final Phone phone = new Phone("Title", 0, 1000.0, "Model", PhoneManufacturer.APPLE);
        target.save(phone);

        ArgumentCaptor<Phone> argument = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(repository).save(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
        Assertions.assertEquals(-1, argument.getValue().getCount());
    }


    @Test
    public void savePhone_verifyTimes() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        target.save(phone);
        ArgumentCaptor<Phone> phoneArgumentCaptor = ArgumentCaptor.forClass(Phone.class);
        verify(repository, times(1)).save(phoneArgumentCaptor.capture());
        Assertions.assertEquals("Title", phoneArgumentCaptor.getValue().getTitle());
    }

    @Test
    public void deletePhone() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        target.delete(phone.getId());
        verify(repository).delete(phone.getId());
    }

    @Test
    public void updatePhone() {
        final Phone phone = target.createProduct();
        when(repository.findById("")).thenReturn(Optional.of(phone));
        target.update(phone);
        verify(repository).update(phone);
    }
}