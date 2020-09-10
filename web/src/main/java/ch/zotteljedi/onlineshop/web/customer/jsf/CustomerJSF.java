package ch.zotteljedi.onlineshop.web.customer.jsf;

import ch.zotteljedi.onlineshop.common.customer.dto.Customer;
import ch.zotteljedi.onlineshop.common.customer.dto.ImmutableCustomer;
import ch.zotteljedi.onlineshop.common.customer.service.CustomerServiceLocal;
import ch.zotteljedi.onlineshop.common.message.Message;
import ch.zotteljedi.onlineshop.web.customer.dto.PageCustomer;
import ch.zotteljedi.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.web.helper.Hash256;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class CustomerJSF implements Serializable {

    @Inject
    private CustomerServiceLocal customerServiceLocal;

    @Inject
    private CustomerSessionJSF customerSessionJSF;

    public void changeUsername(String username) throws UnauthorizedAccessException {
        customerServiceLocal.getCustomerById(customerSessionJSF.getCustomerId()).ifPresent(customer -> {
            Customer changedCustomer = ImmutableCustomer.copyOf(customer)
                    .withUsername(username);
            if (!customerServiceLocal.changeCustomer(changedCustomer)
                    .hasMessagesThenProvide(msg -> showMessage("customerUsernameChangeForm", msg))) {
                showMessage(null, () -> "Username changed.");
            }
        });
        customerSessionJSF.update();
    }

    public void changeCustomer(String firstname, String lastname, String email) throws UnauthorizedAccessException {
        customerServiceLocal.getCustomerById(customerSessionJSF.getCustomerId()).ifPresent(customer -> {
            Customer changedCustomer = ImmutableCustomer.copyOf(customer)
                    .withFirstname(firstname)
                    .withLastname(lastname)
                    .withEmail(email);
            customerServiceLocal.changeCustomer(changedCustomer);
            showMessage(null, () -> "Userdata changed.");
        });
        customerSessionJSF.update();
    }

    public void changePassword(String oldPassword, String newPassword) throws UnauthorizedAccessException {
        PageCustomer customer = customerSessionJSF.getCustomer();
        try {
            final String oldPasswordHashed = Hash256.INSTANCE.hash256(oldPassword);
            final String newPasswordHashed = Hash256.INSTANCE.hash256(newPassword);

            if (customerServiceLocal.checkCredentials(customer.getUsername(), oldPasswordHashed)) {
                customerServiceLocal.getCustomerById(customerSessionJSF.getCustomerId()).ifPresent(resolvedCustomer -> {
                    Customer changedCustomer = ImmutableCustomer.copyOf(resolvedCustomer)
                            .withPassword(newPasswordHashed);
                    customerServiceLocal.changeCustomer(changedCustomer);
                    showMessage(null, () -> "Password changed.");
                });
                return;
            }
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }
        showMessage("customerChangePassordForm", () -> "Invalid credentials.");
    }

    public String deleteCustomer(String password) throws UnauthorizedAccessException {
        try {
            PageCustomer customer = customerSessionJSF.getCustomer();
            if (customerServiceLocal.checkCredentials(customer.getUsername(), Hash256.INSTANCE.hash256(password))) {
                customerServiceLocal.deleteCustomer(customer.getId());
                showMessage(null, () -> "Customer profile successfully deleted.");
                return customerSessionJSF.logout();
            } else {
                showMessage("customerChangePassordForm", () -> "Invalid credentials.");
            }
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }
        return "profile";
    }

    private void showMessage(final String clientId, final Message message) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(message.getMessage()));
    }
}
