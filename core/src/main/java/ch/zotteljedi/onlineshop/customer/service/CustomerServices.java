package ch.zotteljedi.onlineshop.customer.service;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.MessageContainer;
import ch.zotteljedi.onlineshop.customer.mapper.CustomerMapper;
import ch.zotteljedi.onlineshop.customer.services.CustomerServicesLocal;
import ch.zotteljedi.onlineshop.entity.CustomerEntity;
import ch.zotteljedi.onlineshop.helper.ApplicationService;
import ch.zotteljedi.onlineshop.helper.message.CustomerNotFoundByUsername;

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
public class CustomerServices extends ApplicationService implements CustomerServicesLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public MessageContainer addNewCustomer(Customer customer) {
        List<CustomerEntity> customerEntities = getCustomerEntityByUsername(customer.getUsername());

        if (!customerEntities.isEmpty()) {
            addMessage(new CustomerNotFoundByUsername(customer.getUsername()));
        }

        CustomerEntity customerEntity = CustomerMapper.INSTANCE.map(customer);
        em.persist(customerEntity);
        return getMessageContainer();
    }

    @Override
    public Optional<Customer> getCustomerByUsername(String username) {
        return getCustomerEntityByUsername(username)
                .stream()
                .findFirst()
                .map(CustomerMapper.INSTANCE::map);
    }

    private List<CustomerEntity> getCustomerEntityByUsername(String username) {
        return em.createNamedQuery("CustomerEntity.getByUsername", CustomerEntity.class)
                .setParameter("username", username)
                .getResultList();
    }

}
