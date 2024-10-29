package dev.shashiirk.shopmate.exception;

public class OperationFailedException extends RuntimeException {

    public OperationFailedException() {
        super();
    }

    public OperationFailedException(String message) {
        super(message);
    }
}
