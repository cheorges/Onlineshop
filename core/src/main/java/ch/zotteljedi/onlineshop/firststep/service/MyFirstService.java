package ch.zotteljedi.onlineshop.firststep.service;

import ch.zotteljedi.onlineshop.firststep.entity.HelloEntity;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
@Local(MyFirstServiceLocal.class)
@Transactional
public class MyFirstService implements MyFirstServiceLocal {
   @PersistenceContext
   private EntityManager em;

   @Override
   public String setMessage() {
      HelloEntity helloEntity = new HelloEntity();
      helloEntity.setName("This is VERY nice");
      em.persist(helloEntity);
      return "good";
   }

   @Override
   public List<String> getMessage() {
      List<HelloEntity> helloEntities = em.createQuery("select h from HelloEntity h", HelloEntity.class).getResultList();
      return helloEntities.stream().map(it -> it.getName()).collect(Collectors.toList());
   }
}
