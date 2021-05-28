package ch.cheorges.onlineshop.web.customer.jsf;

import ch.cheorges.onlineshop.web.common.Hash256;
import ch.cheorges.onlineshop.web.common.massage.MessageFactory;
import ch.cheorges.onlineshop.web.customer.dto.PageCustomer;
import ch.cheorges.onlineshop.common.customer.dto.Customer;
import ch.cheorges.onlineshop.common.customer.dto.CustomerId;
import ch.cheorges.onlineshop.common.customer.service.CustomerServiceLocal;
import ch.cheorges.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.cheorges.onlineshop.web.customer.mapper.PageCustomerMapper;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class CustomerSessionJSF implements Serializable {

    @Inject
    private CustomerServiceLocal customerServiceLocal;

    @Inject
    private MessageFactory messageFactory;

    private Optional<PageCustomer> authenticatedPersistPageCustomer = Optional.empty();

    public boolean isAuthenticated() {
        return authenticatedPersistPageCustomer.isPresent();
    }

    public CustomerId getCustomerId() throws UnauthorizedAccessException {
        return authenticatedPersistPageCustomer.orElseThrow(UnauthorizedAccessException::new).getId();
    }

    public PageCustomer getCustomer() throws UnauthorizedAccessException {
        return authenticatedPersistPageCustomer.orElseThrow(UnauthorizedAccessException::new);
    }

    public String login(String username, String password) {
        try {
            if (customerServiceLocal.checkCredentials(username, Hash256.INSTANCE.hash256(password))) {
                Optional<Customer> customer = customerServiceLocal.getCustomerByUsername(username);
                authenticatedPersistPageCustomer = Optional.of(PageCustomerMapper.INSTANCE.map(customer.orElseThrow(UnauthorizedAccessException::new)));
                messageFactory.showInfo("Login successful.");
                return "overview";
            }
        } catch (NoSuchAlgorithmException | UnauthorizedAccessException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }

        authenticatedPersistPageCustomer = Optional.empty();
        messageFactory.showError("Invalid credentials.");
        return "login";
    }

    public String logout() {
        authenticatedPersistPageCustomer = Optional.empty();
        messageFactory.showInfo("Logout successful.");
        return "index";
    }

    public void update() throws UnauthorizedAccessException {
        Optional<Customer> customer = customerServiceLocal.getCustomerById(getCustomerId());
        authenticatedPersistPageCustomer = Optional.of(PageCustomerMapper.INSTANCE.map(customer.orElseThrow(UnauthorizedAccessException::new)));
    }
}
