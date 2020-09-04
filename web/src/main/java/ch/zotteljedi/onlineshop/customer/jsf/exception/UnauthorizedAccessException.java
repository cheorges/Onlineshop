package ch.zotteljedi.onlineshop.customer.jsf.exception;

public class UnauthorizedAccessException extends IllegalAccessException {
    public UnauthorizedAccessException() {
        super("This Exception is thrown for security reasons.");
    }
}
