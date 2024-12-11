package dev.shashiirk.shopmate.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("The specified resource does not exist");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
