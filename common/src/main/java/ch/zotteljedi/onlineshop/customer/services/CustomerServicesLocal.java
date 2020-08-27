package ch.zotteljedi.onlineshop.customer.services;

import ch.zotteljedi.onlineshop.customer.dto.Customer;

import javax.ejb.Local;
import java.io.Serializable;

@Local
public interface CustomerServicesLocal extends Serializable {
    void addNewCustomer(Customer customer);
}
