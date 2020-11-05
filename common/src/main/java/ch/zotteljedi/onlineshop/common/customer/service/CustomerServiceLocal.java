package ch.zotteljedi.onlineshop.common.customer.service;

import ch.zotteljedi.onlineshop.common.customer.dto.Customer;
import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.customer.dto.NewCustomer;
import ch.zotteljedi.onlineshop.common.message.MessageContainer;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.Optional;

@Local
public interface CustomerServiceLocal extends Serializable {
    MessageContainer addNewCustomer(NewCustomer newCustomer);

    Optional<Customer> getCustomerByUsername(String username);

    Optional<Customer> getCustomerById(CustomerId id);

    boolean checkCredentials(String username, String password);

    MessageContainer changeCustomer(Customer customer);

    MessageContainer deleteCustomer(CustomerId id);
}
