package com;

import com.command.Commands;
import com.command.Create;
import com.command.Delete;
import com.command.Print;
import com.command.Update;
import com.command.UserInputUtil;
import com.model.Phone;
import com.service.LaptopService;
import com.service.PhoneService;
import com.service.SimpleBinaryTree;
import com.service.TabletService;

import java.util.ArrayList;
import java.util.List;


public class Main {
    private static final PhoneService PHONE_SERVICE = PhoneService.getInstance();
    private static final TabletService TABLET_SERVICE = TabletService.getInstance();
    private static final LaptopService LAPTOP_SERVICE = LaptopService.getInstance();
    private static final SimpleBinaryTree<Phone> SimplePhoneBinaryTree = new SimpleBinaryTree<>();
    private static final SimpleBinaryTree<Phone> SIMPLE_PHONE_BINARY_TREE = SimplePhoneBinaryTree;

    public static void main(String[] args) {
        SIMPLE_PHONE_BINARY_TREE.createAndOutputTree(PHONE_SERVICE);
        final Commands[] values = Commands.values();
        final List<String> names = getNamesOfCommand(values);
        boolean exit = false;
        do {
            int commandIndex = UserInputUtil.getUserInput(values.length, names);
            switch (values[commandIndex]) {
                case DELETE -> new Delete().execute();
                case PRINT -> new Print().execute();
                case CREATE -> new Create().execute();
                case UPDATE -> new Update().execute();
                case EXIT -> exit = true;
            }
        } while (!exit);
    }

    private static List<String> getNamesOfCommand(final Commands[] values) {
        final List<String> names = new ArrayList<>(values.length);
        for (Commands commands : values) {
            names.add(commands.name());
        }
        return names;
    }
}
