package dev.shashiirk.shopmate.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super("Invalid request format or content");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
