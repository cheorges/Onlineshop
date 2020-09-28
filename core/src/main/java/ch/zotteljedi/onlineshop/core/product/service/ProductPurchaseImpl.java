package ch.zotteljedi.onlineshop.core.product.service;

import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.product.dto.Purchase;
import ch.zotteljedi.onlineshop.common.product.service.ProductPurchaseLocal;
import ch.zotteljedi.onlineshop.common.product.service.ProductServicLocal;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.core.product.mapper.PurchaseMapper;
import ch.zotteljedi.onlineshop.core.product.message.ProductByIdNotFound;
import ch.zotteljedi.onlineshop.core.service.ApplicationService;
import ch.zotteljedi.onlineshop.data.entity.PurchaseEntitiy;
import ch.zotteljedi.onlineshop.data.entity.PurchaseItemEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
@Local(ProductServicLocal.class)
@Transactional
public class ProductPurchaseImpl extends ApplicationService implements ProductPurchaseLocal {

   @PersistenceContext
   private EntityManager em;

   @Inject
   private CustomerServiceImpl customerService;

   @Inject
   private ProductServicImpl productService;

   @Override
   public MessageContainer newPurchase(Purchase purchase) {
      final PurchaseEntitiy purchaseEntitiy = new PurchaseEntitiy();
      purchaseEntitiy.setBoughtAt(LocalDate.now());
      purchaseEntitiy.setBuyer(customerService.getCustomerEntityById(purchase.getBuyerId()));

      List<PurchaseItemEntity> purchaseItemEntities = purchase.getCartProduct().stream()
            .map(cartProduct -> {
               PurchaseItemEntity purchaseItemEntity = PurchaseMapper.INSTANCE.map(cartProduct);
               purchaseItemEntity.setPurchase(purchaseEntitiy);
               productService.getProductEntityById(cartProduct.getProductId())
                     .ifPresentOrElse(purchaseItemEntity::setProduct, () -> addMessage(new ProductByIdNotFound(cartProduct.getProductId())));
               return purchaseItemEntity;
            }).collect(Collectors.toList());

      purchaseItemEntities.forEach(it -> em.persist(it));

      return getMessageContainer();
   }
}
