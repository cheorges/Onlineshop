package ch.zotteljedi.onlineshop.core.customer.service;

import ch.zotteljedi.onlineshop.common.customer.dto.Customer;
import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.customer.dto.NewCustomer;
import ch.zotteljedi.onlineshop.common.customer.service.CustomerServiceLocal;
import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.core.customer.mapper.CustomerMapper;
import ch.zotteljedi.onlineshop.core.customer.message.CustomerUsernameAllreadyExist;
import ch.zotteljedi.onlineshop.core.service.ApplicationService;
import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;
import org.hibernate.exception.ConstraintViolationException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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
    public Optional<Customer> getCustomerById(CustomerId id) {
        CustomerEntity customerEntity = getCustomerEntityById(id);
        if (Objects.isNull(customerEntity)) {
            return Optional.empty();
        }
        return Optional.of(CustomerMapper.INSTANCE.map(customerEntity));
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
    public MessageContainer changeCustomer(Customer customer) {
        CustomerEntity customerEntity = CustomerMapper.INSTANCE.map(customer);
        try {
            em.merge(customerEntity);
            em.flush();
        } catch (Exception e) {
            addMessage(new CustomerUsernameAllreadyExist(customer.getUsername()));
        }
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
