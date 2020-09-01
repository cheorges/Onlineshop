package ch.zotteljedi.onlineshop.customer.service;

import ch.zotteljedi.onlineshop.customer.services.CustomerSessionServicesLocal;
import ch.zotteljedi.onlineshop.entity.CustomerEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Local(CustomerSessionServicesLocal.class)
@Transactional
public class CustomerSessionSerivces implements CustomerSessionServicesLocal {

   @PersistenceContext
   private EntityManager em;

   @Override
   public boolean checkCredentials(String username, String password) {
      List<CustomerEntity> customerEntities = em.createNamedQuery("CustomerEntity.getByUsernameAndPassword", CustomerEntity.class)
            .setParameter("username", username)
            .setParameter("password", password)
            .getResultList();
      return !customerEntities.isEmpty();
   }
}
