package ch.zotteljedi.onlineshop.helper.message;

import ch.zotteljedi.onlineshop.common.dto.Message;

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
