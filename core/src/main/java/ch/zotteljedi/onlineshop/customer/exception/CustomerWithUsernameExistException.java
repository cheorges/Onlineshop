package ch.zotteljedi.onlineshop.customer.exception;

public class CustomerWithUsernameExistException extends ApplicationException {
    public CustomerWithUsernameExistException(String username) {
        super("Customer with username: '" + username + "' already exist." );
    }
}
