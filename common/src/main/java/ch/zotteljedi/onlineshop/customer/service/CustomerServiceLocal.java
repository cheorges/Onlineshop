package ch.zotteljedi.onlineshop.customer.service;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.common.dto.MessageContainer;
import ch.zotteljedi.onlineshop.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.customer.dto.NewCustomer;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.Optional;

@Local
public interface CustomerServiceLocal extends Serializable {
    MessageContainer addNewCustomer(NewCustomer newCustomer);
    Optional<Customer> getCustomerByUsername(String username);
    Optional<Customer> getCustomerById(CustomerId customerId);
    boolean checkCredentials(String username, String password);
    MessageContainer changeCustomerUsername(CustomerId id, String username);
    MessageContainer changeCustomerPassword(CustomerId id, String username);
    MessageContainer changeCustomer(CustomerId id, String firstname, String lastname, String email);
    MessageContainer deleteCustomer(CustomerId id);
}
