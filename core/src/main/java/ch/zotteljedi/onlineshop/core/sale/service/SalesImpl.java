package ch.zotteljedi.onlineshop.core.sale.service;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.common.sale.dto.SalesItemOverview;
import ch.zotteljedi.onlineshop.common.sale.dto.SalesOverview;
import ch.zotteljedi.onlineshop.common.sale.service.SalesServiceLocal;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.core.product.service.ProductServiceImpl;
import ch.zotteljedi.onlineshop.core.sale.mapper.SalesMapper;
import ch.zotteljedi.onlineshop.core.service.ApplicationService;
import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import ch.zotteljedi.onlineshop.data.entity.PurchaseItemEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
@Local(SalesServiceLocal.class)
@Transactional
public class SalesImpl extends ApplicationService implements SalesServiceLocal {

   @PersistenceContext(unitName = "ZotteltecPersistenceProvider")
   EntityManager em;

   @Inject
   CustomerServiceImpl customerService;

   @Inject
   ProductServiceImpl productService;

   @Override
   public List<SalesOverview> getSalesByCustomer(CustomerId customerId) {
      return productService.getProductEntitiyByCustomerId(customerId).stream()
            .map(this::getSalesOverview).collect(Collectors.toList());
   }

   @Override
   public Optional<SalesOverview> getSalesByProductId(ProductId purchaseId) {
      Optional<ProductEntity> productEntity = productService.getProductEntityById(purchaseId);
      return productEntity.map(this::getSalesOverview);
   }

   private SalesOverview getSalesOverview(ProductEntity productEntity) {
      List<SalesItemOverview> salesItemOverview = getPurchaseByProduct(productEntity).stream()
            .map(purchaseItemEntity -> SalesMapper.INSTANCE
                  .map(purchaseItemEntity, customerService.buildCustomerRepresentation(purchaseItemEntity.getPurchase().getBuyer())))
            .collect(Collectors.toList());
      return SalesMapper.INSTANCE.map(productEntity, salesItemOverview);
   }

   private List<PurchaseItemEntity> getPurchaseByProduct(final ProductEntity productEntity) {
      return em.createNamedQuery("PurchaseItemEntity.getByProduct", PurchaseItemEntity.class)
            .setParameter("product", productEntity)
            .getResultList();
   }

}
