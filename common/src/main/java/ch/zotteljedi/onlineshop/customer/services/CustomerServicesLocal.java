package ch.zotteljedi.onlineshop.customer.services;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.MessageContainer;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.Optional;

@Local
public interface CustomerServicesLocal extends Serializable {
    MessageContainer addNewCustomer(Customer customer);
    Optional<Customer> getCustomerByUsername(String username);
    boolean checkCredentials(String username, String password);
    MessageContainer changeCustomerUsername(Integer id, String username);
    MessageContainer changeCustomerPassword(Integer id, String username);
    MessageContainer changeCustomer(Integer id, String firstname, String lastname, String email);
}
