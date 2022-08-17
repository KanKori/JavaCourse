package com.command;

import com.command.util.UserInputUtil;
import com.model.product.AbstractProduct;
import com.model.product.specifications.ProductType;
import com.service.product.laptop.LaptopServiceAbstract;
import com.service.product.phone.PhoneServiceAbstract;
import com.service.product.AbstractProductService;
import com.service.product.tablet.TabletServiceAbstract;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Update implements Command {
    private static final PhoneServiceAbstract PHONE_SERVICE = PhoneServiceAbstract.getInstance();
    private static final LaptopServiceAbstract LAPTOP_SERVICE = LaptopServiceAbstract.getInstance();
    private static final TabletServiceAbstract TABLET_SERVICE = TabletServiceAbstract.getInstance();

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

    private void update(AbstractProductService<? extends AbstractProduct> service) {
        while (true) {
            System.out.println("Enter product ID");
            try {
                String id;
                do {
                    id = SCANNER.nextLine();
                } while (id.length() == 0);
                AbstractProduct abstractProduct = service.findByIdOrElseThrow(id);
                final List<String> names = Arrays.asList("Update Title", "Update Price", "Update Count");
                int stop;
                do {
                    int productTypeIndex = UserInputUtil.getUserInput(names.size(), names);
                    switch (names.get(productTypeIndex)) {
                        case "Update Title" -> updateTitle(abstractProduct);
                        case "Update Price" -> updatePrice(abstractProduct);
                        case "Update Count" -> updateCount(abstractProduct);
                    }
                    System.out.println("Enter -1 to complete the update, or any other number to continue updating");
                    stop = SCANNER.nextInt();
                } while (stop != -1);
                service.update(abstractProduct);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong ID. Try again");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updatePrice(AbstractProduct abstractProduct) throws IOException {
        System.out.println("Enter new Price");
        String price = SCANNER.nextLine();
        if (StringUtils.isNumeric(price)) {
            abstractProduct.setPrice(Long.parseLong(price));
        } else {
            System.out.println("Wrong input");
            updatePrice(abstractProduct);
        }
    }

    private void updateTitle(AbstractProduct abstractProduct) throws IOException {
        System.out.println("Enter new Title");
        String title = SCANNER.nextLine();
        abstractProduct.setTitle(title);
    }

    private void updateCount(AbstractProduct abstractProduct) throws IOException {
        System.out.println("Enter new Count");
        String count = SCANNER.nextLine();
        if (StringUtils.isNumeric(count)) {
            abstractProduct.setCount(Integer.parseInt(count));
        } else {
            System.out.println("Wrong input");
            updateCount(abstractProduct);
        }
    }
}
