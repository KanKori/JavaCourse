package com.command;

import com.model.ProductType;
import com.service.ProductFactory;

import java.util.ArrayList;
import java.util.List;

public class Create implements Command {
    @Override
    public void execute() {
        System.out.println("What do you want to create:");
        final ProductType[] values = ProductType.values();
        final List<String> names = getNamesOfType(values);
        final int userInput = UserInputUtil.getUserInput(values.length, names);
        ProductFactory.createAndSave(values[userInput]);
    }

    private List<String> getNamesOfType(final ProductType[] values) {
        final List<String> names = new ArrayList<>(values.length);
        for (ProductType type : values) {
            names.add(type.name());
        }
        return names;
    }
}
