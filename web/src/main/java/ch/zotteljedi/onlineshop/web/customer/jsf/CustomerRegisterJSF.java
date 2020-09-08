package ch.zotteljedi.onlineshop.web.customer.jsf;


import ch.zotteljedi.onlineshop.common.customer.dto.ImmutableNewCustomer;
import ch.zotteljedi.onlineshop.common.customer.dto.NewCustomer;
import ch.zotteljedi.onlineshop.common.customer.service.CustomerServiceLocal;
import ch.zotteljedi.onlineshop.common.message.Message;
import ch.zotteljedi.onlineshop.web.customer.dto.PageCustomer;
import ch.zotteljedi.onlineshop.web.helper.Hash256;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

@Named
@RequestScoped
public class CustomerRegisterJSF implements Serializable {

    @Inject
    private CustomerServiceLocal customerServiceLocal;

    private final PageCustomer registerPageCustomer = new PageCustomer();

    public PageCustomer getRegisterPageCustomer() {
        return registerPageCustomer;
    }

    public String register() {
        try {
            NewCustomer newCustomer = ImmutableNewCustomer.builder()
                    .username(getRegisterPageCustomer().getUsername())
                    .firstname(getRegisterPageCustomer().getFirstname())
                    .lastname(getRegisterPageCustomer().getLastname())
                    .email(getRegisterPageCustomer().getEmail())
                    .password(Hash256.INSTANCE.hash256(getRegisterPageCustomer().getPassword()))
                    .build();
            if (!customerServiceLocal.addNewCustomer(newCustomer)
                    .hasMessagesThenProvide(msg -> showMessage("customerRegisterForm", msg))) {
                showMessage(null, () -> "Registration successful.");
                return "login";
            }
        } catch (NoSuchAlgorithmException e) {
            showMessage("customerRegisterForm", () -> "No hash value of the password could be generated. No profile was created.");
        }
        return "register";
    }

    private void showMessage(final String clientId, final Message message) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(message.getMessage()));
    }
}
