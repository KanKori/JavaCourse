package com.exception.file.read;

import java.io.IOException;

public class InvalidLineException extends IOException {
    public InvalidLineException(String message) {
        super(message);
    }
}
