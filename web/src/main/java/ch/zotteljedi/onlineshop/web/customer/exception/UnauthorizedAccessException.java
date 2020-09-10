package ch.zotteljedi.onlineshop.web.customer.exception;

public class UnauthorizedAccessException extends IllegalAccessException {
    public UnauthorizedAccessException() {
        super("This Exception is thrown for security reasons.");
    }
}
