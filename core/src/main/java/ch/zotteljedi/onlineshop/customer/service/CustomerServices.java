package ch.zotteljedi.onlineshop.customer.service;

import ch.zotteljedi.onlineshop.customer.dto.Customer;
import ch.zotteljedi.onlineshop.customer.mapper.CustomerMapper;
import ch.zotteljedi.onlineshop.customer.services.CustomerServicesLocal;
import ch.zotteljedi.onlineshop.entity.CustomerEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Local(CustomerServicesLocal.class)
@Transactional
public class CustomerServices implements CustomerServicesLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addNewCustomer(Customer customer) {
        List<CustomerEntity> customerEntities = em.createNamedQuery("CustomerEntity.getByUsername", CustomerEntity.class)
                .setParameter("username", customer.getUsername())
                .getResultList();

        if (customerEntities.size() != 0) {
            throw new IllegalArgumentException("Username already exists.");
        }

        CustomerEntity customerEntity = CustomerMapper.INSTANCE.map(customer);
        em.persist(customerEntity);
    }
}
