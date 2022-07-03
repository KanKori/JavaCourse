package com;

import com.service.LaptopService;
import com.service.PhoneService;
import com.service.TabletService;


public class Main {
    private static final PhoneService PHONE_SERVICE = new PhoneService();
    private static final LaptopService LAPTOP_SERVICE = new LaptopService();
    private static final TabletService TABLET_SERVICE = new TabletService();

    public static void main(String[] args) {
        PHONE_SERVICE.createAndSavePhones(3);
        PHONE_SERVICE.printAll();
        LAPTOP_SERVICE.createAndSaveLaptops(3);
        LAPTOP_SERVICE.printAll();
        TABLET_SERVICE.createAndSaveTablets(3);
        TABLET_SERVICE.printAll();
        // TODO: 02/07/22  add tests
    }
}
