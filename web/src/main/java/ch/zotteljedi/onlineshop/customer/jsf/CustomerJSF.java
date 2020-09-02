package ch.zotteljedi.onlineshop.customer.jsf;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.ImmutableCustomer;
import ch.zotteljedi.onlineshop.customer.dto.Message;
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

@Named
@SessionScoped
public class CustomerJSF implements Serializable {

    @Inject
    private CustomerServicesLocal customerServicesLocal;

    @Inject
    private CustomerSessionJSF customerSessionJSF;

    public void addNewCustomer(String username, String firstname, String lastname, String email, String password) {
        try {
            Customer customer = ImmutableCustomer.builder()
                    .username(username)
                    .firstname(firstname)
                    .lastname(lastname)
                    .email(email)
                    .password(Hash256.INSTANCE.hash256(password))
                    .build();
            customerServicesLocal.addNewCustomer(customer)
                    .hasMessagesThenProvide((msg) -> showMessage("customerRegisterForm", msg));
        } catch (NoSuchAlgorithmException e) {
            showMessage("customerRegisterForm", (Message) () -> "No hash value of the password could be generated. No profile was created.");
        }
    }

    public void changeUsername(String username) throws UnauthorizedAccessException {
            if (!customerServicesLocal.changeCustomerUsername(customerSessionJSF.getCustomer().getId(), username)
                    .hasMessagesThenProvide((msg) -> showMessage("customerUsernameChangeForm", msg))) {
                showMessage("customerUsernameChangeForm", (Message) () -> "Username changed.");
            }
    }

    public void changePassword(String oldPassord, String newPassord) throws UnauthorizedAccessException {
        try {
            Customer customer = customerSessionJSF.getCustomer();
            if (oldPassord.equals(newPassord)
                    && customerServicesLocal.checkCredentials(customer.getUsername(), Hash256.INSTANCE.hash256(oldPassord))) {
                customerServicesLocal.changeCustomerPassword(customer.getId(), Hash256.INSTANCE.hash256(newPassord));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void changeCustomer(String firstname, String lastname, String email) throws UnauthorizedAccessException {
        customerServicesLocal.changeCustomer(customerSessionJSF.getCustomer().getId(), firstname, lastname, email);
        customerSessionJSF.update();
    }

    private void showMessage(final String clientId, final Message message) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(message.getMessage()));
    }
}
