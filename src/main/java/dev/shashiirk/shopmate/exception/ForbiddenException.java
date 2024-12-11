package dev.shashiirk.shopmate.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("You are not permitted to access this content");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
