package ch.zotteljedi.onlineshop.customer.services;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.exception.ApplicationException;

import java.io.Serializable;
import javax.ejb.Local;

@Local
public interface CustomerServicesLocal extends Serializable {
    void addNewCustomer(Customer customer) throws ApplicationException;
    Customer getCustomerByUsername(String username) throws ApplicationException;
}
