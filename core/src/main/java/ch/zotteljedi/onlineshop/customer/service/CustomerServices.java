package ch.zotteljedi.onlineshop.customer.service;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.MessageContainer;
import ch.zotteljedi.onlineshop.customer.mapper.CustomerMapper;
import ch.zotteljedi.onlineshop.customer.services.CustomerServicesLocal;
import ch.zotteljedi.onlineshop.entity.CustomerEntity;
import ch.zotteljedi.onlineshop.helper.ApplicationService;
import ch.zotteljedi.onlineshop.helper.message.CustomerUsernameAllreadyExist;

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
        if (customerEntities.isEmpty()) {
            CustomerEntity customerEntity = CustomerMapper.INSTANCE.map(customer);
            em.persist(customerEntity);
        } else {
            addMessage(new CustomerUsernameAllreadyExist(customer.getUsername()));
        }
        return getMessageContainer();
    }

    @Override
    public Optional<Customer> getCustomerByUsername(String username) {
        return getCustomerEntityByUsername(username)
                .stream()
                .findFirst()
                .map(CustomerMapper.INSTANCE::map);
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        List<CustomerEntity> customerEntities = em.createNamedQuery("CustomerEntity.getByUsernameAndPassword", CustomerEntity.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
        return !customerEntities.isEmpty();
    }

    @Override
    public MessageContainer changeCustomerUsername(Integer id, String username) {
        if (getCustomerEntityByUsername(username).isEmpty()) {
            CustomerEntity customerEntity = getCustomerEntityById(id);
            customerEntity.setUsername(username);
        } else {
            addMessage(new CustomerUsernameAllreadyExist(username));
        }
        return getMessageContainer();
    }

    @Override
    public MessageContainer changeCustomerPassword(Integer id, String username) {
        CustomerEntity customerEntity = getCustomerEntityById(id);
        customerEntity.setPassword(username);
        return getMessageContainer();
    }

    @Override
    public MessageContainer changeCustomer(Integer id, String firstname, String lastname, String email) {
        CustomerEntity customerEntity = getCustomerEntityById(id);
        customerEntity.setFirstname(firstname);
        customerEntity.setLastname(lastname);
        customerEntity.setEmail(email);
        return getMessageContainer();
    }

    private CustomerEntity getCustomerEntityById(Integer id) {
        return em.createNamedQuery("CustomerEntity.getById", CustomerEntity.class)
                .setParameter("id", id)
                .getSingleResult();
    }


    private List<CustomerEntity> getCustomerEntityByUsername(String username) {
        return em.createNamedQuery("CustomerEntity.getByUsername", CustomerEntity.class)
                .setParameter("username", username)
                .getResultList();
    }

}
