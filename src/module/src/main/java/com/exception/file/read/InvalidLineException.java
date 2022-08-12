package com.exception.file.read;

import java.io.IOException;

public class InvalidLineException extends IOException {
    public InvalidLineException(String message) {
        super(message);
    }

    public String toString() {
        return "Read error. Invalid string specified";
    }
}
