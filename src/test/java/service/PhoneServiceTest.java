package service;

import com.model.Phone;
import com.model.PhoneManufacturer;
import com.repository.PhoneRepository;
import com.service.PhoneService;
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
        assertEquals("Title", argument.getValue().getTitle());
    }

    @Test
    void savePhone_zeroCount() {
        final Phone phone = new Phone("Title", 0, 1000.0, "Model", PhoneManufacturer.APPLE);
        target.save(phone);

        ArgumentCaptor<Phone> argument = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(repository).save(argument.capture());
        assertEquals("Title", argument.getValue().getTitle());
        assertEquals(-1, argument.getValue().getCount());
    }


    @Test
    public void savePhone_verifyTimes() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        target.save(phone);
        ArgumentCaptor<Phone> phoneArgumentCaptor = ArgumentCaptor.forClass(Phone.class);
        verify(repository, times(1)).save(phoneArgumentCaptor.capture());
        assertEquals("Title", phoneArgumentCaptor.getValue().getTitle());
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

    @Test
    public void deleteIfPresent() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        when(repository.findById(anyString())).thenReturn(Optional.of(phone));
        target.deleteIfPresent(phone.getId());
        verify(repository).delete(phone.getId());
    }

    @Test
    public void doNotDeleteIfMissing() {
        final String ghostId = target.createPhone().getId();
        target.deleteIfPresent(ghostId);
        verify(repository, times(0)).delete(ghostId);
    }

    @Test
    public void updateIfPresent() {
        final Phone phone = target.createPhone();
        when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        target.updateIfPresentOrElseSaveNew(phone);
        verify(repository).update(phone);
    }

    @Test
    public void updateOrElseSaveNew() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        target.updateIfPresentOrElseSaveNew(phone);
        verify(repository).save(phone);
    }

    @Test
    public void findByIdOrElseRandom() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        target.findByIdOrElseRandom(phone.getId());
        verify(repository).getRandomPhone();
    }

    @Test
    public void findByIdOrElseGetRandom() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        target.findByIdOrElseGetRandom(phone.getId());
        verify(repository).getRandomPhone();
    }

    @Test
    public void findByIdOrElseThrow() {
        String incorrectId = "x";
        when(repository.findById(incorrectId)).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findByIdOrElseThrow("incorrectId"));
    }

    @Test
    public void deletePhoneFindByIdIfManufacturerApple() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        when(repository.findById(anyString())).thenReturn(Optional.of(phone));
        target.deletePhoneFindByIdIfManufacturerApple(phone.getId());
        verify(repository).delete(phone.getId());
    }

    @Test
    public void deletePhoneFindByIdIfManufacturerApple_notApple() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.SAMSUNG);
        target.deletePhoneFindByIdIfManufacturerApple(phone.getId());
        verify(repository, times(0)).delete(phone.getId());
    }

    @Test
    public void findByIdOrGetAny() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        when(repository.findById(anyString())).thenReturn(Optional.of(phone));
        target.findByIdOrGetAny(phone);
        assertEquals(target.findByIdOrGetAny(phone), Optional.of(phone));
    }

    @Test
    public void mapFromPhoneToString() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        when(repository.findById(anyString())).thenReturn(Optional.of(phone));
        assertEquals(target.mapFromPhoneToString(phone), phone.toString());
    }

    @Test
    public void mapFromPhoneToString_null() {
        final Phone phone = new Phone("Title", 100, 1000.0, "Model", PhoneManufacturer.APPLE);
        when(repository.findById(anyString())).thenReturn(Optional.of(phone));
        assertNotEquals(target.mapFromPhoneToString(phone), null);
    }
}