package ch.zotteljedi.onlineshop.helper.message;

import ch.zotteljedi.onlineshop.customer.dto.Message;

public class CustomerNotFoundByUsername implements Message {

    private final String username;

    public CustomerNotFoundByUsername(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "Customer by username '" + username + "' not found.";
    }
}
