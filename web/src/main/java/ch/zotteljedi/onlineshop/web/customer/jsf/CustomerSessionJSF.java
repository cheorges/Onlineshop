package ch.zotteljedi.onlineshop.web.customer.jsf;

import ch.zotteljedi.onlineshop.common.customer.dto.Customer;
import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.web.customer.dto.LoginPageCustomer;
import ch.zotteljedi.onlineshop.web.customer.dto.PersistPageCustomer;
import ch.zotteljedi.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.web.customer.mapper.PublicCustomerMapper;
import ch.zotteljedi.onlineshop.common.customer.service.CustomerServiceLocal;
import ch.zotteljedi.onlineshop.web.helper.Hash256;

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
    private CustomerServiceLocal customerServiceLocal;

    private final LoginPageCustomer loginPageCustomer = new LoginPageCustomer();
    private Optional<PersistPageCustomer> authenticatedPersistPageCustomer = Optional.empty();

    public LoginPageCustomer getLoginPageCustomer() {
        return loginPageCustomer;
    }

    public boolean isAuthenticated() {
        return authenticatedPersistPageCustomer.isPresent();
    }

    public CustomerId getCustomerId() throws UnauthorizedAccessException {
        return authenticatedPersistPageCustomer.orElseThrow(UnauthorizedAccessException::new).getId();
    }

    public PersistPageCustomer getPersistPageCustomer() throws UnauthorizedAccessException {
        return authenticatedPersistPageCustomer.orElseThrow(UnauthorizedAccessException::new);
    }

    public String login() {
        loginPageCustomer.reset();
        try {
            if (customerServiceLocal.checkCredentials(getLoginPageCustomer().getUsername(), Hash256.INSTANCE.hash256(getLoginPageCustomer().getPassword()))) {
                Optional<Customer> customer = customerServiceLocal.getCustomerByUsername(getLoginPageCustomer().getUsername());
                authenticatedPersistPageCustomer = Optional.of(PublicCustomerMapper.INSTANCE.map(customer.orElseThrow(UnauthorizedAccessException::new)));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login successful."));
                return "customer_overviewproduct";
            }
        } catch (NoSuchAlgorithmException | UnauthorizedAccessException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }

        authenticatedPersistPageCustomer = Optional.empty();
        FacesContext.getCurrentInstance().addMessage("customerLoginForm", new FacesMessage("Invalid credentials."));
        return "login";
    }

    public String logout() {
        authenticatedPersistPageCustomer = Optional.empty();
        return "index";
    }

    public void update() throws UnauthorizedAccessException {
        Optional<Customer> customer = customerServiceLocal.getCustomerById(getCustomerId());
        authenticatedPersistPageCustomer = Optional.of(PublicCustomerMapper.INSTANCE.map(customer.orElseThrow(UnauthorizedAccessException::new)));
    }
}
