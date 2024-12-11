package dev.shashiirk.shopmate.exception;

public class EmailAlreadyUsedException extends BadRequestException {

    public EmailAlreadyUsedException() {
        super("Email is already in use");
    }

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
