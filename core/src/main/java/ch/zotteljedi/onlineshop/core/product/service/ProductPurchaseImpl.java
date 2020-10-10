package ch.zotteljedi.onlineshop.core.product.service;

import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.product.dto.CartProduct;
import ch.zotteljedi.onlineshop.common.product.dto.Purchase;
import ch.zotteljedi.onlineshop.common.product.service.ProductPurchaseLocal;
import ch.zotteljedi.onlineshop.common.product.service.ProductServicLocal;
import ch.zotteljedi.onlineshop.core.customer.message.CustomerByIdNotFound;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.core.product.mapper.PurchaseMapper;
import ch.zotteljedi.onlineshop.core.product.message.ProductByIdNotFound;
import ch.zotteljedi.onlineshop.core.service.ApplicationService;
import ch.zotteljedi.onlineshop.data.entity.PurchaseEntity;
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

   @PersistenceContext(unitName = "ZotteltecPersistenceProvider")
   private EntityManager em;

   @Inject
   private CustomerServiceImpl customerService;

   @Inject
   private ProductServicImpl productService;

   @Override
   public MessageContainer newPurchase(Purchase purchase) {
      final PurchaseEntity purchaseEntity = new PurchaseEntity();
      purchaseEntity.setBoughtAt(LocalDate.now());
      customerService.getCustomerEntityById(purchase.getBuyerId())
            .ifPresentOrElse(purchaseEntity::setBuyer, () -> addMessage(new CustomerByIdNotFound(purchase.getBuyerId())));
      em.persist(purchaseEntity);

      List<PurchaseItemEntity> purchaseItemEntities = purchase.getCartProduct().stream()
            .map(cartProduct -> {
               return getPurchaseItemEntity(purchaseEntity, cartProduct);
            }).collect(Collectors.toList());

      getMessageContainer().hasNoMessageThenProvide(() -> purchaseItemEntities.forEach(it -> em.persist(it)));

      return getMessageContainer();
   }

   private PurchaseItemEntity getPurchaseItemEntity(final PurchaseEntity purchaseEntity, final CartProduct cartProduct) {
      productService.getProductById(cartProduct.getProductId())
            .ifPresentOrElse(product -> productService.removeStockByProductId(product.getId(), cartProduct.getUnit()).hasMessagesThenProvide(this::addMessage),
                  () -> addMessage(new ProductByIdNotFound(cartProduct.getProductId())));
      PurchaseItemEntity purchaseItemEntity = PurchaseMapper.INSTANCE.map(cartProduct);
      purchaseItemEntity.setPurchase(purchaseEntity);
      productService.getProductEntityById(cartProduct.getProductId())
            .ifPresentOrElse(purchaseItemEntity::setProduct, () -> addMessage(new ProductByIdNotFound(cartProduct.getProductId())));
      return purchaseItemEntity;
   }
}
