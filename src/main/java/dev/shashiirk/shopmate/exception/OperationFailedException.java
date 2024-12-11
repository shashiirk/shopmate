package dev.shashiirk.shopmate.exception;

public class OperationFailedException extends RuntimeException {

    public OperationFailedException() {
        super("An error occurred while processing your request");
    }

    public OperationFailedException(String message) {
        super(message);
    }
}
