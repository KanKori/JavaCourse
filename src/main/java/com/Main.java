package com;

import com.model.Phone;
import com.model.Tablet;
import com.repository.PhoneRepository;
import com.repository.TabletRepository;
import com.service.LaptopService;
import com.service.PhoneService;
import com.service.TabletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final PhoneService PHONE_SERVICE = new PhoneService();
    private static final LaptopService LAPTOP_SERVICE = new LaptopService();
    private static final TabletService TABLET_SERVICE = new TabletService();
    private static final TabletRepository TABLET_REPOSITORY = new TabletRepository();

    public static void main(String[] args) {
        PHONE_SERVICE.createAndSavePhones(3);
        LOGGER.info("Create and save phones");
        PHONE_SERVICE.printAll();
        LAPTOP_SERVICE.createAndSaveLaptops(3);
        LOGGER.info("Create and save laptops");
        LAPTOP_SERVICE.printAll();
        TABLET_SERVICE.createAndSaveTablets(3);
        LOGGER.info("Create and save tablets");
        TABLET_SERVICE.printAll();

        // TODO: 02/07/22  add tests
    }
}
