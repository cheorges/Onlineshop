package ch.cheorges.onlineshop.web.customer.jsf;

import ch.cheorges.onlineshop.web.common.Hash256;
import ch.cheorges.onlineshop.web.common.massage.MessageFactory;
import ch.cheorges.onlineshop.web.customer.dto.PageCustomer;
import ch.cheorges.onlineshop.common.customer.dto.Customer;
import ch.zotteljedi.onlineshop.common.customer.dto.ImmutableCustomer;
import ch.cheorges.onlineshop.common.customer.service.CustomerServiceLocal;
import ch.cheorges.onlineshop.web.customer.exception.UnauthorizedAccessException;

import javax.enterprise.context.SessionScoped;
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

    @Inject
    private MessageFactory messageFactory;

    public void changeCustomer(String username, String firstname, String lastname, String email) throws UnauthorizedAccessException {
        customerServiceLocal.getCustomerById(customerSessionJSF.getCustomerId()).ifPresent(customer -> {
            Customer changedCustomer = ImmutableCustomer.copyOf(customer)
                    .withUsername(username)
                    .withFirstname(firstname)
                    .withLastname(lastname)
                    .withEmail(email);
            if (!customerServiceLocal.changeCustomer(changedCustomer)
                    .hasMessagesThenProvide(msg -> messageFactory.showError(msg))) {
                messageFactory.showInfo("Customer userdata changed.");
            }
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
                    messageFactory.showInfo("Password changed.");
                });
                return;
            }
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }
        messageFactory.showError("Invalid credentials.");
    }

    public String deleteCustomer(String password) throws UnauthorizedAccessException {
        try {
            PageCustomer customer = customerSessionJSF.getCustomer();
            if (customerServiceLocal.checkCredentials(customer.getUsername(), Hash256.INSTANCE.hash256(password))) {
                customerServiceLocal.deleteCustomer(customer.getId());
                messageFactory.showInfo("Customer profile successfully deleted.");
                return customerSessionJSF.logout();
            } else {
                messageFactory.showError("Invalid credentials.");
            }
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }
        return "profile";
    }

}
