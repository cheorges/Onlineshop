package ch.zotteljedi.onlineshop.customer.jsf;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.ImmutableCustomer;
import ch.zotteljedi.onlineshop.customer.dto.Message;
import ch.zotteljedi.onlineshop.customer.dto.MessageContainer;
import ch.zotteljedi.onlineshop.customer.services.CustomerServicesLocal;
import ch.zotteljedi.onlineshop.helper.Hash256;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class CustomerJSF implements Serializable {

    @Inject
    private CustomerServicesLocal customerServicesLocal;

    public void addNewCustomer(String username, String firstname, String lastname, String email, String password) {
        try {
            Customer customer = ImmutableCustomer.builder()
                    .username(username)
                    .firstname(firstname)
                    .lastname(lastname)
                    .email(email)
                    .password(Hash256.INSTANCE.hash256(password))
                    .build();

            MessageContainer messageContainer = customerServicesLocal.addNewCustomer(customer);
            if (!messageContainer.hasMessagesThenProvide((msg) -> showMessage(FacesMessage.SEVERITY_ERROR, msg))) {
                showMessage(FacesMessage.SEVERITY_INFO, new Message() {
                    @Override
                    public String getMessage() {
                        return "Registration successfully.";
                    }
                });
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect("index.xhtml");
            }

        } catch (Exception e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }
    }

    private void showMessage(final FacesMessage.Severity severity, final Message message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message.getMessage(), ""));
    }
}
