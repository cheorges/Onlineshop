package ch.zotteljedi.onlineshop.customer.exception;

public abstract class ApplicationException extends Exception {

    private final String message;

    protected ApplicationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
