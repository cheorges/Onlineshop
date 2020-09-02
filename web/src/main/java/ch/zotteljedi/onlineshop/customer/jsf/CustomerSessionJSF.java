package ch.zotteljedi.onlineshop.customer.jsf;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.services.CustomerServicesLocal;
import ch.zotteljedi.onlineshop.helper.Hash256;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class CustomerSessionJSF implements Serializable {

    private static final String IS_AUTHENTICATED_KEY = "IS_AUTHENTICATED_KEY";
    private static final String EMPTY = "";

    @Inject
    private CustomerServicesLocal customerServicesLocal;

    public boolean isAuthenticated() {
        return getAuthenticatedCustomer().isPresent();
    }

    public String getCustomerRepresentation() {
        final Optional<Customer> authenticatedCustomer = getAuthenticatedCustomer();
        if (authenticatedCustomer.isPresent()) {
            return authenticatedCustomer.get().getRepresentation();
        }
        forceLogout();
        return EMPTY;
    }

    public void login(final String username, final String password) {
        final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            if (customerServicesLocal.checkCredentials(username, Hash256.INSTANCE.hash256(password))) {
                final Optional<Customer> authentificatedCustomer = customerServicesLocal.getCustomerByUsername(username);
                if (authentificatedCustomer.isPresent()) {
                    externalContext.getSessionMap().put(IS_AUTHENTICATED_KEY, authentificatedCustomer.get());
                    externalContext.redirect("index.xhtml");
                    return;
                } else {
                    // The customer should always be present at this point. Otherwise the check of the credentials data would fail.
                    Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, "Customer by username not found.");
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }

        externalContext.getSessionMap().put(IS_AUTHENTICATED_KEY, Optional.empty());
        FacesContext.getCurrentInstance().addMessage("customerLoginForm", new FacesMessage("Invalid credentials."));
    }

    public void logout() {
        forceLogout();
    }

    private void forceLogout() {
        final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getSessionMap().put(IS_AUTHENTICATED_KEY, Optional.empty());
        try {
            externalContext.redirect("index.xhtml");
        } catch (IOException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }
    }

    private Optional<Customer> getAuthenticatedCustomer() {
        final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        final Object obj = externalContext.getSessionMap().get(IS_AUTHENTICATED_KEY);
        return (!Objects.isNull(obj) && obj instanceof Customer) ? Optional.of((Customer) obj) : Optional.empty();
    }

}
