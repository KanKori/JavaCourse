package com.command;

import com.command.util.UserInputUtil;
import com.model.product.AbstractProduct;
import com.model.product.specifications.ProductType;
import com.service.product.laptop.LaptopServiceAbstract;
import com.service.product.phone.PhoneServiceAbstract;
import com.service.product.AbstractProductService;
import com.service.product.tablet.TabletServiceAbstract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Delete implements Command {
    private static final PhoneServiceAbstract PHONE_SERVICE = PhoneServiceAbstract.getInstance();
    private static final LaptopServiceAbstract LAPTOP_SERVICE = LaptopServiceAbstract.getInstance();
    private static final TabletServiceAbstract TABLET_SERVICE = TabletServiceAbstract.getInstance();
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        System.out.println("What product do you want to delete");
        ProductType[] types = ProductType.values();
        final List<String> names = getNamesOfType(types);
        int productTypeIndex = UserInputUtil.getUserInput(types.length, names);
        switch (types[productTypeIndex]) {
            case PHONE -> delete(PHONE_SERVICE);
            case LAPTOP -> delete(LAPTOP_SERVICE);
            case TABLET -> delete(TABLET_SERVICE);
            default -> throw new IllegalStateException("Unknown type");
        }
    }

    private List<String> getNamesOfType(final ProductType[] values) {
        final List<String> names = new ArrayList<>(values.length);
        for (ProductType type : values) {
            names.add(type.name());
        }
        return names;
    }

    private void delete(AbstractProductService<? extends AbstractProduct> service) {
        while (true) {
            System.out.println("Enter product ID");
            try {
                String id = READER.readLine();
                service.findByIdOrElseThrow(id);
                service.delete(id);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong ID. Try again");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
