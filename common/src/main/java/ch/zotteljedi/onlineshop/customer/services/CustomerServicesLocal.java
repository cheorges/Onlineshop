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
}
