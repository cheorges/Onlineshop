package ch.zotteljedi.onlineshop.core.customer.service;

import ch.zotteljedi.onlineshop.common.customer.dto.Customer;
import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.customer.dto.NewCustomer;
import ch.zotteljedi.onlineshop.common.customer.service.CustomerServiceLocal;
import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.core.customer.mapper.CustomerMapper;
import ch.zotteljedi.onlineshop.core.customer.message.CustomerByIdNotFound;
import ch.zotteljedi.onlineshop.core.customer.message.CustomerUsernameAllreadyExist;
import ch.zotteljedi.onlineshop.core.service.ApplicationService;
import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;

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
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@Local(CustomerServiceLocal.class)
@Transactional
public class CustomerServiceImpl extends ApplicationService implements CustomerServiceLocal {

    @PersistenceContext
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

    private List<CustomerEntity> getCustomerEntityByUsername(String username) {
        return em.createNamedQuery("CustomerEntity.getByUsername", CustomerEntity.class)
                .setParameter("username", username)
                .getResultList();
    }

    public Optional<CustomerEntity> getCustomerEntityById(CustomerId id) {
        return em.createNamedQuery("CustomerEntity.getById", CustomerEntity.class)
                .setParameter("id", id.getValue())
                .getResultList()
                .stream()
                .findFirst();
    }

    private boolean validate(CustomerEntity customer) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customer);
        List<String> collect = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        collect.forEach(message -> addMessage(() -> message));
        return !getMessageContainer().hasMessages();
    }

}
