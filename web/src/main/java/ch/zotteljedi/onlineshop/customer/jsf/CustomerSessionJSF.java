package ch.zotteljedi.onlineshop.customer.jsf;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.jsf.dto.PublicCustomer;
import ch.zotteljedi.onlineshop.customer.jsf.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.customer.jsf.mapper.PublicCustomerMapper;
import ch.zotteljedi.onlineshop.customer.services.CustomerServicesLocal;
import ch.zotteljedi.onlineshop.helper.Hash256;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class CustomerSessionJSF implements Serializable {

    @Inject
    private CustomerServicesLocal customerServicesLocal;

    private Optional<Customer> authenticatedPrivateCustomer = Optional.empty();
    private Optional<PublicCustomer> authenticatedPublicCustomer;

    public boolean isAuthenticated() {
        return authenticatedPrivateCustomer.isPresent();
    }

    public PublicCustomer getCustomer() throws UnauthorizedAccessException {
        return authenticatedPublicCustomer.orElseThrow(UnauthorizedAccessException::new);
    }

    public void login(final String username, final String password) {
        try {
            if (customerServicesLocal.checkCredentials(username, Hash256.INSTANCE.hash256(password))) {
                authenticatedPrivateCustomer = customerServicesLocal.getCustomerByUsername(username);
                authenticatedPublicCustomer = Optional.of(PublicCustomerMapper.INSTANCE.map(authenticatedPrivateCustomer.orElseThrow(UnauthorizedAccessException::new)));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login successful."));
                return;
            }
        } catch (NoSuchAlgorithmException | UnauthorizedAccessException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }

        authenticatedPrivateCustomer = Optional.empty();
        FacesContext.getCurrentInstance().addMessage("customerLoginForm", new FacesMessage("Invalid credentials."));
    }

    public void logout() {
        authenticatedPrivateCustomer = Optional.empty();
    }

    void update() throws UnauthorizedAccessException {
//        authenticatedCustomer = Optional.of(ImmutableCustomer.copyOf(authenticatedCustomer.orElseThrow(UnauthorizedAccessException::new))
//              .withEmail(customer.getEmail())
//              .withFirstname(customer.getFirstname())
//              .withLastname(customer.getLastname())
//              .withUsername(customer.getUsername()));
        Customer privateCustomer = authenticatedPrivateCustomer.orElseThrow(UnauthorizedAccessException::new);
        PublicCustomer customer = authenticatedPublicCustomer.orElseThrow(UnauthorizedAccessException::new);
        privateCustomer.setEmail(customer.getEmail());
        privateCustomer.setFirstname(customer.getFirstname());
        privateCustomer.setLastname(customer.getLastname());
        privateCustomer.setUsername(customer.getUsername());
    }

}
