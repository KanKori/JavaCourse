package com.command;

import lombok.Getter;

@Getter
public enum Commands {
    CREATE("Create product", new Create()),
    UPDATE("Update product", new Update()),
    PRINT("Print products", new Print()),
    DELETE("Delete products", new Delete()),
    EXIT("Exit", null);

    private final String name;
    private final Command command;

    Commands(String name, Command command) {
        this.name = name;
        this.command = command;
    }
}