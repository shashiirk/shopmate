package dev.shashiirk.shopmate.exception;

public class EmailAlreadyUsedException extends BadRequestException {

    public EmailAlreadyUsedException() {
        super();
    }

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
