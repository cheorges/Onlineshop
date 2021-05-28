package ch.cheorges.onlineshop.core.customer.service;

import ch.cheorges.onlineshop.core.customer.mapper.CustomerMapper;
import ch.cheorges.onlineshop.core.service.ApplicationService;
import ch.cheorges.onlineshop.common.customer.dto.Customer;
import ch.cheorges.onlineshop.common.customer.dto.CustomerId;
import ch.cheorges.onlineshop.common.customer.dto.NewCustomer;
import ch.cheorges.onlineshop.common.customer.service.CustomerServiceLocal;
import ch.cheorges.onlineshop.common.message.MessageContainer;
import ch.cheorges.onlineshop.core.customer.message.CustomerByIdNotFound;
import ch.cheorges.onlineshop.core.customer.message.CustomerUsernameAllreadyExist;
import ch.cheorges.onlineshop.data.entity.CustomerEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

@Stateless
@Local(CustomerServiceLocal.class)
@Transactional
public class CustomerServiceImpl extends ApplicationService implements CustomerServiceLocal {

    @PersistenceContext(unitName = "ZotteltecPersistenceProvider")
    EntityManager em;

    @Override
    public MessageContainer addNewCustomer(NewCustomer newCustomer) {
        List<CustomerEntity> customerEntities = getCustomerEntityByUsername(newCustomer.getUsername());
        if (customerEntities.isEmpty()) {
            CustomerEntity customerEntity = CustomerMapper.INSTANCE.map(newCustomer);
            if (validate(customerEntity)) {
                em.persist(customerEntity);
            }
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
        return getCustomerEntityById(id).map(CustomerMapper.INSTANCE::map);
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
            if (validate(customerEntity)) {
                em.merge(customerEntity);
                em.flush();
            }
        } catch (Exception e) {
            addMessage(new CustomerUsernameAllreadyExist(customer.getUsername()));
        }
        return getMessageContainer();
    }

    @Override
    public MessageContainer deleteCustomer(CustomerId id) {
        getCustomerEntityById(id).ifPresentOrElse(customer -> em.remove(customer), () -> addMessage(new CustomerByIdNotFound(id)));
        return getMessageContainer();
    }


    public Optional<CustomerEntity> getCustomerEntityById(CustomerId id) {
        return em.createNamedQuery("CustomerEntity.getById", CustomerEntity.class)
                .setParameter("id", id.getValue())
                .getResultList()
                .stream()
                .findFirst();
    }

    public String buildCustomerRepresentation(final CustomerEntity customer) {
        return customer.getFirstname() + " " + customer.getLastname();
    }

    private List<CustomerEntity> getCustomerEntityByUsername(String username) {
        return em.createNamedQuery("CustomerEntity.getByUsername", CustomerEntity.class)
                .setParameter("username", username)
                .getResultList();
    }

}
