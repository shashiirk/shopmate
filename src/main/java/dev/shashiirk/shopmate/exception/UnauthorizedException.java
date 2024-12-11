package dev.shashiirk.shopmate.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Authentication required to view this content.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
