package com.command;

import com.model.Product;
import com.model.ProductType;
import com.service.LaptopService;
import com.service.PhoneService;
import com.service.ProductService;
import com.service.TabletService;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Update implements Command {
    private static final PhoneService PHONE_SERVICE = PhoneService.getInstance();
    private static final LaptopService LAPTOP_SERVICE = LaptopService.getInstance();
    private static final TabletService TABLET_SERVICE = TabletService.getInstance();

    @Override
    public void execute() {
        System.out.println("What do you want to update:");
        ProductType[] types = ProductType.values();
        final List<String> names = getNamesOfType(types);
        int productTypeIndex = UserInputUtil.getUserInput(types.length, names);
        switch (types[productTypeIndex]) {
            case PHONE -> update(PHONE_SERVICE);
            case LAPTOP -> update(LAPTOP_SERVICE);
            case TABLET -> update(TABLET_SERVICE);
            default -> throw new IllegalStateException("Unknown ProductType " + types[productTypeIndex]);
        }
    }

    private List<String> getNamesOfType(final ProductType[] values) {
        final List<String> names = new ArrayList<>(values.length);
        for (ProductType type : values) {
            names.add(type.name());
        }
        return names;
    }

    private void update(ProductService<? extends Product> service) {
        while (true) {
            System.out.println("Enter product ID");
            try {
                String id;
                do {
                    id = SCANNER.nextLine();
                } while (id.length() == 0);
                Product product = service.findByIdOrElseThrow(id);
                final List<String> names = Arrays.asList("Update Title", "Update Price", "Update Count");
                int stop;
                do {
                    int productTypeIndex = UserInputUtil.getUserInput(names.size(), names);
                    switch (names.get(productTypeIndex)) {
                        case "Update Title" -> updateTitle(product);
                        case "Update Price" -> updatePrice(product);
                        case "Update Count" -> updateCount(product);
                    }
                    System.out.println("Enter -1 to complete the update, or any other number to continue updating");
                    stop = SCANNER.nextInt();
                } while (stop != -1);
                service.update(product);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong ID. Try again");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updatePrice(Product product) throws IOException {
        System.out.println("Enter new Price");
        String price = SCANNER.nextLine();
        if (StringUtils.isNumeric(price)) {
            product.setPrice(Long.parseLong(price));
        } else {
            System.out.println("Wrong input");
            updatePrice(product);
        }
    }

    private void updateTitle(Product product) throws IOException {
        System.out.println("Enter new Title");
        String title = SCANNER.nextLine();
        product.setTitle(title);
    }

    private void updateCount(Product product) throws IOException {
        System.out.println("Enter new Count");
        String count = SCANNER.nextLine();
        if (StringUtils.isNumeric(count)) {
            product.setCount(Integer.parseInt(count));
        } else {
            System.out.println("Wrong input");
            updateCount(product);
        }
    }
}
