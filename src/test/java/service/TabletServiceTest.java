package service;

import com.model.Tablet;
import com.model.TabletManufacturer;
import com.repository.TabletRepository;
import com.service.TabletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveTablets(-1));
    }

    @Test
    void createAndSaveTablets_zeroCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveTablets(0));
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
        ArgumentCaptor<Tablet> tabletArgumentCaptor = ArgumentCaptor.forClass(Tablet.class);
        verify(repository, times(1)).save(tabletArgumentCaptor.capture());
        Assertions.assertEquals("Title", tabletArgumentCaptor.getValue().getTitle());
    }

    @Test
    public void deleteTablet() {
        final Tablet tablet = new Tablet("Title", 100, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        target.delete(tablet.getId());
        verify(repository).delete(tablet.getId());
    }

    @Test
    public void updateTablet() {
        final Tablet tablet = target.createTablet();
        when(repository.findById("")).thenReturn(Optional.of(tablet));
        target.update(tablet);
        verify(repository).update(tablet);
    }

    @Test
    public void deleteIfPresent() {
        final Tablet tablet = new Tablet("Title", 100, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        when(repository.findById(anyString())).thenReturn(Optional.of(tablet));
        target.deleteIfPresent(tablet.getId());
        verify(repository).delete(tablet.getId());
    }

    @Test
    public void doNotDeleteIfMissing() {
        final String ghostId = target.createTablet().getId();
        target.deleteIfPresent(ghostId);
        verify(repository, times(0)).delete(ghostId);
    }

    @Test
    public void updateIfPresent() {
        final Tablet tablet = target.createTablet();
        when(repository.findById(tablet.getId())).thenReturn(Optional.of(tablet));
        target.updateIfPresentOrElseSaveNew(tablet);
        verify(repository).update(tablet);
    }

    @Test
    public void updateOrElseSaveNew() {
        final Tablet tablet = new Tablet("Title", 100, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        target.updateIfPresentOrElseSaveNew(tablet);
        verify(repository).save(tablet);
    }

    @Test
    public void findByIdOrElseRandom() {
        final Tablet tablet = new Tablet("Title", 100, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        target.findByIdOrElseRandom(tablet.getId());
        verify(repository).getRandomTablet();
    }

    @Test
    public void findByIdOrElseGetRandom() {
        final Tablet tablet = new Tablet("Title", 100, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        target.findByIdOrElseGetRandom(tablet.getId());
        verify(repository).getRandomTablet();
    }

    @Test
    public void findByIdOrElseThrow() {
        String incorrectId = "x";
        when(repository.findById(incorrectId)).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findByIdOrElseThrow("incorrectId"));
    }

    @Test
    public void deleteTabletFindByIdIfManufacturerGoogle() {
        final Tablet tablet = new Tablet("Title", 100, 1000.0, "Model", TabletManufacturer.GOOGLE);
        when(repository.findById(anyString())).thenReturn(Optional.of(tablet));
        target.deleteTabletFindByIdIfManufacturerGoogle(tablet.getId());
        verify(repository).delete(tablet.getId());
    }

    @Test
    public void deleteTabletFindByIdIfManufacturerGoogle_notGoogle() {
        final Tablet tablet = new Tablet("Title", 100, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        target.deleteTabletFindByIdIfManufacturerGoogle(tablet.getId());
        verify(repository, times(0)).delete(tablet.getId());
    }

    @Test
    public void findByIdOrGetAny() {
        final Tablet tablet = new Tablet("Title", 100, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        when(repository.findById(anyString())).thenReturn(Optional.of(tablet));
        target.findByIdOrGetAny(tablet);
        assertEquals(target.findByIdOrGetAny(tablet), Optional.of(tablet));
    }
}
