package ch.cheorges.onlineshop.core.customer.message;

import ch.cheorges.onlineshop.common.message.Message;

public class CustomerUsernameAllreadyExist implements Message {

    private final String username;

    public CustomerUsernameAllreadyExist(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "Username '" + username + "' already exist.";
    }
}
