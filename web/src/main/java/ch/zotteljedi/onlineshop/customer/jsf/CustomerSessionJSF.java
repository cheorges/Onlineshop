package ch.zotteljedi.onlineshop.customer.jsf;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.ImmutableCustomer;
import ch.zotteljedi.onlineshop.customer.exception.ApplicationException;
import ch.zotteljedi.onlineshop.customer.services.CustomerServicesLocal;
import ch.zotteljedi.onlineshop.customer.services.CustomerSessionServicesLocal;
import ch.zotteljedi.onlineshop.helper.Hash256;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class CustomerSessionJSF implements Serializable {

    private static final String IS_AUTHENTICATED_KEY = "IS_AUTHENTICATED_KEY";

    @Inject
    private CustomerSessionServicesLocal customerSessionServicesLocal;

    @Inject
    private CustomerServicesLocal customerServicesLocal;

    public boolean isAuthenticated() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Object obj = externalContext.getSessionMap().get(IS_AUTHENTICATED_KEY);
        if (!Objects.isNull(obj) && obj instanceof Customer) {
            Customer customer = (Customer) obj;
            return customer.getAuthenticated();
        }
        return false;
    }

    public void login(String username, String password) {
        try {
            if (customerSessionServicesLocal.checkCredantioals(username, Hash256.INSTANCE.hash256(password))) {
                Customer customer = customerServicesLocal.getCustomerByUsername(username);
                showMessage(FacesMessage.SEVERITY_INFO, "Successfully signed in.");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.getSessionMap().put(IS_AUTHENTICATED_KEY, ImmutableCustomer.copyOf(customer).withAuthenticated(true));
                externalContext.redirect("index.xhtml");
            }
        } catch (ApplicationException e) {
            showMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }

        showMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials");
    }

    private void showMessage(final FacesMessage.Severity severity, final String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, ""));
    }

}
