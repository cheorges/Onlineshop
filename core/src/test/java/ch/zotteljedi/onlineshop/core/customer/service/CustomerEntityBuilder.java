package ch.zotteljedi.onlineshop.core.customer.service;

import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;

import java.security.SecureRandom;

public class CustomerEntityBuilder {
    private final CustomerEntity customerEntity = new CustomerEntity();

    public CustomerEntityBuilder id(Integer id) {
        customerEntity.setId(id);
        return this;
    }

    public CustomerEntityBuilder firstname(String firstname) {
        customerEntity.setFirstname(firstname);
        return this;
    }

    public CustomerEntityBuilder lastname(String lastname) {
        customerEntity.setLastname(lastname);
        return this;
    }

    public CustomerEntityBuilder username(String username) {
        customerEntity.setUsername(username);
        return this;
    }

    public CustomerEntityBuilder password(String password) {
        customerEntity.setPassword(password);
        return this;
    }

    public CustomerEntityBuilder email(String email) {
        customerEntity.setEmail(email);
        return this;
    }

    public CustomerEntity build() {
        return customerEntity;
    }
}
