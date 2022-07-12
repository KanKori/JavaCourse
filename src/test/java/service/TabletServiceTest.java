package service;

import com.model.Tablet;
import com.model.PhoneManufacturer;
import com.model.Tablet;
import com.model.TabletManufacturer;
import com.repository.TabletRepository;
import com.service.TabletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TabletServiceTest {

    private TabletService target;
    private TabletRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(TabletRepository.class);
        target = new TabletService(repository);
    }

    @Test
    void createAndSaveTablets_negativeCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->  target.createAndSaveTablets(-1));
    }

    @Test
    void createAndSaveTablets_zeroCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->  target.createAndSaveTablets(0));
    }

    @Test
    void createAndSaveTablets() {
        target.createAndSaveTablets(2);
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
    void saveTablet() {
        final Tablet tablet = new Tablet("Title", 100, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        target.saveTablet(tablet);

        ArgumentCaptor<Tablet> argument = ArgumentCaptor.forClass(Tablet.class);
        Mockito.verify(repository).save(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
    }

    @Test
    void saveTablet_zeroCount() {
        final Tablet tablet = new Tablet("Title", 0, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        target.saveTablet(tablet);

        ArgumentCaptor<Tablet> argument = ArgumentCaptor.forClass(Tablet.class);
        Mockito.verify(repository).save(argument.capture());
        Assertions.assertEquals("Title", argument.getValue().getTitle());
        Assertions.assertEquals(-1, argument.getValue().getCount());
    }

    @Test
    public void saveTablet_verifyTimes() {
        final Tablet tablet = new Tablet("Title", 100, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        target.saveTablet(tablet);
        ArgumentCaptor<Tablet> phoneArgumentCaptor = ArgumentCaptor.forClass(Tablet.class);
        verify(repository, times(1)).save(phoneArgumentCaptor.capture());
        Assertions.assertEquals("Title", phoneArgumentCaptor.getValue().getTitle());
    }

    @Test
    public void updateTablet() {
        target.createAndSaveTablets(2);
        final List<Tablet> phoneList = target.getAll();
        final int index = new Random().nextInt(phoneList.size());
        final Tablet tablet = (repository.findById(phoneList.get(index).getId()).get());
        final Tablet updatedPhone = target.createTablet();
        when(repository.findById(tablet.getId()).get()).thenReturn(tablet);
        target.update(updatedPhone);
        verify(repository).save(updatedPhone);
    }
}
