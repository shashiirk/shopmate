package dev.shashiirk.shopmate.exception;

public class UnsupportedOperationException extends RuntimeException {

    public UnsupportedOperationException() {
        super("This action cannot be performed");
    }

    public UnsupportedOperationException(String message) {
        super(message);
    }
}
