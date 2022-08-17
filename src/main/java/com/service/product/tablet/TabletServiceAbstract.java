package com.service.product.tablet;

import com.model.product.tablet.Tablet;
import com.model.product.tablet.specifications.TabletManufacturer;
import com.repository.product.tablet.TabletRepositoryI;
import com.service.product.AbstractProductService;

import java.util.Random;

public class TabletServiceAbstract extends AbstractProductService<Tablet> {
    private static final Random RANDOM = new Random();
    private static TabletServiceAbstract instance;

    public TabletServiceAbstract(TabletRepositoryI repository) {
        super(repository);
    }

    public static TabletServiceAbstract getInstance() {
        if (instance == null) {
            instance = new TabletServiceAbstract(TabletRepositoryI.getInstance());
        }
        return instance;
    }

    public Tablet createProduct() {
        return new Tablet(
                "Title-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer());
    }

    private TabletManufacturer getRandomManufacturer() {
        final TabletManufacturer[] values = TabletManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void deleteTabletFindByIdIfManufacturerGoogle(String id) {
        getRepository().findById(id)
                .filter(checkingTablet -> checkingTablet.getTabletManufacturer().equals(TabletManufacturer.GOOGLE))
                .ifPresentOrElse(checkedTablet -> getRepository().delete(checkedTablet.getId()),
                        () -> System.out.println("no one Google tablet founded"));
    }

    public boolean checkDetailExists(String detailToCheck) {
        return getAll().stream().flatMap(phone -> phone.getDetails().stream())
                .anyMatch(detail -> detail.equals(detailToCheck));
    }
}
