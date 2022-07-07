package com;

import com.model.Laptop;
import com.model.Tablet;
import com.service.LaptopService;
import com.service.PhoneService;
import com.service.TabletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;


public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final PhoneService PHONE_SERVICE = new PhoneService();
    private static final LaptopService LAPTOP_SERVICE = new LaptopService();
    private static final TabletService TABLET_SERVICE = new TabletService();

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

        final List<Laptop> laptopList = LAPTOP_SERVICE.getFullList();
        final int index = new Random().nextInt(laptopList.size());
        LAPTOP_SERVICE.delete(laptopList.get(index).getId());
        LOGGER.info("Removed random Laptop");
        LAPTOP_SERVICE.printAll();

        final List<Tablet> tabletList = TABLET_SERVICE.getFullList();
        final int index2 = new Random().nextInt(tabletList.size());
        tabletList.get(index2).setTitle("NEW " + tabletList.get(index2).getTitle());
        TABLET_SERVICE.update(tabletList.get(index2));
        LOGGER.info("Update Tablet");
        TABLET_SERVICE.printAll();
    }
}
