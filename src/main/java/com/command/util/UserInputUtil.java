package com.command.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class UserInputUtil {
    private UserInputUtil() {
    }

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static int getUserInput(int length, List<String> names) {
        final int NONE_TYPE = -1;
        int userType;
        do {
            userType = getUserInput(names, length);
        } while (userType == NONE_TYPE);
        return userType;
    }

    private static int getUserInput(List<String> names, int length) {
        final int FIRST_ELEMENT = 1;
        try {
            for (int i = 0; i < length; i++) {
                System.out.printf("%d) %s%n", i + 1, names.get(i));
            }
            int input = Integer.parseInt(READER.readLine());
            if (input >= FIRST_ELEMENT && input < length + FIRST_ELEMENT) {
                return input - FIRST_ELEMENT;
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Input is not valid");
        }
        return -1;
    }
}
