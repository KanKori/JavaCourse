package repository;

import com.model.product.tablet.Tablet;
import com.model.product.tablet.specifications.TabletManufacturer;
import com.repository.product.tablet.TabletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.spy;

public class TabletRepositoryTest {

    private TabletRepository target;

    private Tablet tablet;

    @BeforeEach
    void setUp() {
        final Random random = new Random();
        target = new TabletRepository();
        tablet = new Tablet(
                "Title-" + random.nextInt(1000),
                random.nextInt(500),
                random.nextDouble(1000.0),
                "Model-" + random.nextInt(10),
                TabletManufacturer.MICROSOFT
        );
    }

    @Test
    void save() {
        target.save(tablet);
        final List<Tablet> tablets = target.getAll();
        Assertions.assertEquals(1, tablets.size());
        Assertions.assertEquals(tablets.get(0).getId(), tablet.getId());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
        final List<Tablet> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void saveAll_singleTablet() {
        target.saveAll(Collections.singletonList(tablet));
        final List<Tablet> tablets = target.getAll();
        Assertions.assertEquals(1, tablets.size());
        Assertions.assertEquals(tablets.get(0).getId(), tablet.getId());
    }

    @Test
    void saveAll_noTablet() {
        target.saveAll(Collections.emptyList());
        final List<Tablet> tablets = target.getAll();
        Assertions.assertEquals(0, tablets.size());
    }

    @Test
    void saveAll_manyTablets() {
        final Tablet otherTablet = new Tablet("Title", 500, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        target.saveAll(List.of(tablet, otherTablet));
        final List<Tablet> tablets = target.getAll();
        Assertions.assertEquals(2, tablets.size());
        Assertions.assertEquals(tablets.get(0).getId(), tablet.getId());
        Assertions.assertEquals(tablets.get(1).getId(), otherTablet.getId());
    }

    @Test
    void saveAll_hasDuplicates() {
        final List<Tablet> tablets = new ArrayList<>();
        tablets.add(tablet);
        tablets.add(tablet);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(tablets));
    }

    @Test
    void saveAll_hasNull() {
        final List<Tablet> tablets = new ArrayList<>();
        tablets.add(tablet);
        tablets.add(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(tablets));
        final List<Tablet> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void update() {
        final String newTitle = "New title";
        target.save(tablet);
        tablet.setTitle(newTitle);

        final boolean result = target.update(tablet);

        Assertions.assertTrue(result);
        final List<Tablet> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(newTitle, actualResult.get(0).getTitle());
        Assertions.assertEquals(tablet.getId(), actualResult.get(0).getId());
        Assertions.assertEquals(tablet.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void update_noTablet() {
        target.save(tablet);
        final Tablet noTablet = new Tablet("Title", 500, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        final boolean result = target.update(noTablet);

        Assertions.assertFalse(result);
        final List<Tablet> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(tablet.getId(), actualResult.get(0).getId());
        Assertions.assertEquals(tablet.getCount(), actualResult.get(0).getCount());
    }

    @Test
    void delete() {
        target.save(tablet);
        final boolean result = target.delete(tablet.getId());
        Assertions.assertTrue(result);
        final List<Tablet> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void delete_noTablet() {
        target.save(tablet);
        final Tablet noTablet = new Tablet("Title", 500, 1000.0, "Model", TabletManufacturer.MICROSOFT);
        final boolean result = target.delete(noTablet.getId());
        Assertions.assertFalse(result);
        final List<Tablet> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll() {
        target.save(tablet);
        final List<Tablet> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll_noTablets() {
        final List<Tablet> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void findById() {
        target.save(tablet);
        final Optional<Tablet> optionalTablet = target.findById(tablet.getId());
        Assertions.assertTrue(optionalTablet.isPresent());
        final Tablet actualTablet = optionalTablet.get();
        Assertions.assertEquals(tablet.getId(), actualTablet.getId());
    }

    @Test
    void findById_CallingRealMethods() {
        TabletRepository target = spy(TabletRepository.class);
        doCallRealMethod().when(target).findById(anyString());
        target.findById(anyString());
    }
}
