package com.command;

import com.model.product.specifications.ProductType;
import com.service.product.laptop.LaptopServiceAbstract;
import com.service.product.phone.PhoneServiceAbstract;
import com.service.product.tablet.TabletServiceAbstract;

public class Print implements Command {
    private static final PhoneServiceAbstract PHONE_SERVICE = PhoneServiceAbstract.getInstance();
    private static final LaptopServiceAbstract LAPTOP_SERVICE = LaptopServiceAbstract.getInstance();
    private static final TabletServiceAbstract TABLET_SERVICE = TabletServiceAbstract.getInstance();

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
