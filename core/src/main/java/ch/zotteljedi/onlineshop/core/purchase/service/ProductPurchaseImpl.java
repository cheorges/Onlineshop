package ch.zotteljedi.onlineshop.core.purchase.service;

import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.common.purchase.dto.CartProduct;
import ch.zotteljedi.onlineshop.common.purchase.dto.Purchase;
import ch.zotteljedi.onlineshop.common.purchase.service.ProductPurchaseLocal;
import ch.zotteljedi.onlineshop.common.product.service.ProductServiceLocal;
import ch.zotteljedi.onlineshop.core.customer.message.CustomerByIdNotFound;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.core.purchase.mapper.PurchaseMapper;
import ch.zotteljedi.onlineshop.core.purchase.message.EmptyCart;
import ch.zotteljedi.onlineshop.core.product.message.ProductByIdNotFound;
import ch.zotteljedi.onlineshop.core.product.service.ProductServiceImpl;
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
@Local(ProductServiceLocal.class)
@Transactional
public class ProductPurchaseImpl extends ApplicationService implements ProductPurchaseLocal {

   @PersistenceContext(unitName = "ZotteltecPersistenceProvider")
   EntityManager em;

   @Inject
   CustomerServiceImpl customerService;

   @Inject
   ProductServiceImpl productService;

   @Override
   public MessageContainer newPurchase(Purchase purchase) {
      if (purchase.getCartProduct().isEmpty()) {
         addMessage(new EmptyCart());
         return getMessageContainer();
      }

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
      productService.getProductEntityById(cartProduct.getProductId())
            .ifPresentOrElse(product -> productService.removeStockByProductId(Id.of(product.getId(), ProductId.class), cartProduct.getUnit()).hasMessagesThenProvide(this::addMessage),
                  () -> addMessage(new ProductByIdNotFound(cartProduct.getProductId())));
      PurchaseItemEntity purchaseItemEntity = PurchaseMapper.INSTANCE.map(cartProduct);
      purchaseItemEntity.setPurchase(purchaseEntity);
      productService.getProductEntityById(cartProduct.getProductId())
            .ifPresentOrElse(purchaseItemEntity::setProduct, () -> addMessage(new ProductByIdNotFound(cartProduct.getProductId())));
      return purchaseItemEntity;
   }
}
