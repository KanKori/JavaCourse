package exception;

public class InvalidLineException extends Exception {
    public String toString() {
        return "Read error. Invalid string specified";
    }
}
