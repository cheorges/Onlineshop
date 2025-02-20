package ch.cheorges.onlineshop.web.customer.jsf;


import ch.cheorges.onlineshop.web.common.massage.MessageFactory;
import ch.cheorges.onlineshop.common.customer.dto.ImmutableNewCustomer;
import ch.cheorges.onlineshop.common.customer.dto.NewCustomer;
import ch.cheorges.onlineshop.common.customer.service.CustomerServiceLocal;
import ch.cheorges.onlineshop.web.common.Hash256;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

@Named
@RequestScoped
public class CustomerRegisterJSF implements Serializable {

    @Inject
    private CustomerServiceLocal customerServiceLocal;

    @Inject
    private MessageFactory messageFactory;

    public String register(String username, String firstname, String lastname, String email, String password) {
        try {
            NewCustomer newCustomer = ImmutableNewCustomer.builder()
                    .username(username)
                    .firstname(firstname)
                    .lastname(lastname)
                    .email(email)
                    .password(Hash256.INSTANCE.hash256(password))
                    .build();
            if (!customerServiceLocal.addNewCustomer(newCustomer)
                    .hasMessagesThenProvide(msg -> messageFactory.showError(msg))) {
                messageFactory.showInfo("Registration successful.");
                return "login";
            }
        } catch (NoSuchAlgorithmException e) {
            messageFactory.showError("No hash value of the password could be generated. No profile was created.");
        }
        return "register";
    }

}
