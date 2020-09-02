package ch.zotteljedi.onlineshop.customer.jsf;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.jsf.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.customer.services.CustomerServicesLocal;
import ch.zotteljedi.onlineshop.helper.Hash256;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class CustomerSessionJSF implements Serializable {

    @Inject
    private CustomerServicesLocal customerServicesLocal;

    private Optional<Customer> authenticatedCustomer = Optional.empty();

    public boolean isAuthenticated() {
        return authenticatedCustomer.isPresent();
    }

    public Customer getCustomer() throws UnauthorizedAccessException {
        return authenticatedCustomer.orElseThrow(UnauthorizedAccessException::new);
    }

    public void login(final String username, final String password) {
        try {
            if (customerServicesLocal.checkCredentials(username, Hash256.INSTANCE.hash256(password))) {
                authenticatedCustomer = customerServicesLocal.getCustomerByUsername(username);
                return;
            }
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }

        authenticatedCustomer = Optional.empty();
        FacesContext.getCurrentInstance().addMessage("customerLoginForm", new FacesMessage("Invalid credentials."));
    }

    public void logout() {
        authenticatedCustomer = Optional.empty();
    }

    public void update() throws UnauthorizedAccessException {
        if (isAuthenticated()) {
            login(getCustomer().getUsername(), getCustomer().getPassword());
        }
    }

}
