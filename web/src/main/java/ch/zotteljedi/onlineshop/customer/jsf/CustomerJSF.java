package ch.zotteljedi.onlineshop.customer.jsf;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.ImmutableCustomer;
import ch.zotteljedi.onlineshop.customer.services.CustomerServicesLocal;
import ch.zotteljedi.onlineshop.firststep.service.MyFirstServiceLocal;
import ch.zotteljedi.onlineshop.helper.Hash256;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CustomerJSF implements Serializable {

    @Inject
    private CustomerServicesLocal customerServicesLocal;

    private Customer customer;

    public void addNewCustomer(String username, String firstname, String lastname, String email, String password) {
        try {
            customer = ImmutableCustomer.builder()
                    .username(username)
                    .firstname(firstname)
                    .lastname(lastname)
                    .email(email)
                    .password(Hash256.INSTANCE.hash256(password))
                    .build();
            customerServicesLocal.addNewCustomer(customer);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("index.xhtml");
            showErrorMessage("Registration successfully");
        } catch (Exception e) {
            showErrorMessage(e.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}
