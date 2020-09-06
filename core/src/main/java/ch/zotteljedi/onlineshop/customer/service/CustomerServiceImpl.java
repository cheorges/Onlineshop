package ch.zotteljedi.onlineshop.customer.service;

import ch.zotteljedi.onlineshop.common.dto.MessageContainer;
import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.customer.dto.NewCustomer;
import ch.zotteljedi.onlineshop.customer.dto.mapper.CustomerMapper;
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
@Local(CustomerServiceLocal.class)
@Transactional
public class CustomerServiceImpl extends ApplicationService implements CustomerServiceLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public MessageContainer addNewCustomer(NewCustomer newCustomer) {
        List<CustomerEntity> customerEntities = getCustomerEntityByUsername(newCustomer.getUsername());
        if (customerEntities.isEmpty()) {
            CustomerEntity customerEntity = CustomerMapper.INSTANCE.map(newCustomer);
            em.persist(customerEntity);
        } else {
            addMessage(new CustomerUsernameAllreadyExist(newCustomer.getUsername()));
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
    public MessageContainer changeCustomerUsername(CustomerId id, String username) {
        if (getCustomerEntityByUsername(username).isEmpty()) {
            CustomerEntity customerEntity = getCustomerEntityById(id);
            customerEntity.setUsername(username);
        } else {
            addMessage(new CustomerUsernameAllreadyExist(username));
        }
        return getMessageContainer();
    }

    @Override
    public MessageContainer changeCustomerPassword(CustomerId id, String username) {
        CustomerEntity customerEntity = getCustomerEntityById(id);
        customerEntity.setPassword(username);
        return getMessageContainer();
    }

    @Override
    public MessageContainer changeCustomer(CustomerId id, String firstname, String lastname, String email) {
        CustomerEntity customerEntity = getCustomerEntityById(id);
        customerEntity.setFirstname(firstname);
        customerEntity.setLastname(lastname);
        customerEntity.setEmail(email);
        return getMessageContainer();
    }

    @Override
    public MessageContainer deleteCustomer(CustomerId id) {
        em.remove(getCustomerEntityById(id));
        return getMessageContainer();
    }

    public CustomerEntity getCustomerEntityById(CustomerId id) {
        return em.createNamedQuery("CustomerEntity.getById", CustomerEntity.class)
                .setParameter("id", id.getValue())
                .getSingleResult();
    }


    private List<CustomerEntity> getCustomerEntityByUsername(String username) {
        return em.createNamedQuery("CustomerEntity.getByUsername", CustomerEntity.class)
                .setParameter("username", username)
                .getResultList();
    }

}
