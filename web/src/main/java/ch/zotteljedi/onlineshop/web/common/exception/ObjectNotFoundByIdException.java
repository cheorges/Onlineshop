package ch.zotteljedi.onlineshop.web.common.exception;

public class ObjectNotFoundByIdException extends IllegalArgumentException {
    public ObjectNotFoundByIdException() {
        super("No object found by id.");
    }
}
