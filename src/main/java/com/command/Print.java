package com.command;

import com.model.product.specifications.ProductType;
import com.service.product.laptop.LaptopService;
import com.service.product.phone.PhoneService;
import com.service.product.tablet.TabletService;

public class Print implements Command {
    private static final PhoneService PHONE_SERVICE = PhoneService.getInstance();
    private static final LaptopService LAPTOP_SERVICE = LaptopService.getInstance();
    private static final TabletService TABLET_SERVICE = TabletService.getInstance();

    @Override
    public void execute() {
        System.out.println("What do you want to print:");
        final ProductType[] values = ProductType.values();
        int userType = -1;
        do {
            for (int i = 0; i < values.length; i++) {
                System.out.printf("%d) %s%n", i, values[i].name());
            }
            int input = SCANNER.nextInt();
            if (input >= 0 && input < values.length) {
                userType = input;
            }
        } while (userType == -1);

        switch (values[userType]) {
            case PHONE -> PHONE_SERVICE.printAll();
            case LAPTOP -> LAPTOP_SERVICE.printAll();
            case TABLET -> TABLET_SERVICE.printAll();
            default -> throw new IllegalArgumentException("Unknown ProductType " + values[userType]);
        }
    }
}
