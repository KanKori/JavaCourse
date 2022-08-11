package com.exception.file.read;

public class InvalidLineException extends Exception {
    public String toString() {
        return "Read error. Invalid string specified";
    }
}
