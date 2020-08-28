package ch.zotteljedi.onlineshop.customer.service;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.exception.ApplicationException;
import ch.zotteljedi.onlineshop.customer.exception.CustomerNotFoundByUsernameException;
import ch.zotteljedi.onlineshop.customer.exception.CustomerWithUsernameExistException;
import ch.zotteljedi.onlineshop.customer.mapper.CustomerMapper;
import ch.zotteljedi.onlineshop.customer.services.CustomerServicesLocal;
import ch.zotteljedi.onlineshop.entity.CustomerEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Stateless
@Local(CustomerServicesLocal.class)
@Transactional
public class CustomerServices implements CustomerServicesLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addNewCustomer(Customer customer) throws ApplicationException {
        List<CustomerEntity> customerEntities = getCustomerEntityByUsername(customer.getUsername());

        if (!customerEntities.isEmpty()) {
            throw new CustomerWithUsernameExistException(customer.getUsername());
        }

        CustomerEntity customerEntity = CustomerMapper.INSTANCE.map(customer);
        em.persist(customerEntity);
    }

    @Override
    public Customer getCustomerByUsername(String username) throws ApplicationException {
        CustomerEntity customerEntity = getCustomerEntityByUsername(username)
              .stream()
              .findFirst().orElseThrow(() -> new CustomerNotFoundByUsernameException(username));
        return CustomerMapper.INSTANCE.map(customerEntity);
    }

    private List<CustomerEntity> getCustomerEntityByUsername(String username) {
        return em.createNamedQuery("CustomerEntity.getByUsername", CustomerEntity.class)
                .setParameter("username", username)
                .getResultList();
    }

}
