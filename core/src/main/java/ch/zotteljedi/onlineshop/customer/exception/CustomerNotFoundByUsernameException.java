package ch.zotteljedi.onlineshop.customer.exception;

public class CustomerNotFoundByUsernameException extends ApplicationException {
    public CustomerNotFoundByUsernameException(String username) {
        super("Customer by username '" + username + "' not found.");
    }
}
