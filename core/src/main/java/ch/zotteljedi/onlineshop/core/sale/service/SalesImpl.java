package ch.zotteljedi.onlineshop.core.sale.service;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.product.dto.Product;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.common.purchase.dto.PurchaseId;
import ch.zotteljedi.onlineshop.common.purchase.dto.PurchaseItemOverview;
import ch.zotteljedi.onlineshop.common.purchase.dto.PurchaseOverview;
import ch.zotteljedi.onlineshop.common.purchase.service.PurchaseServiceLocal;
import ch.zotteljedi.onlineshop.common.sale.dto.ImmutableSalesItemOverview;
import ch.zotteljedi.onlineshop.common.sale.dto.ImmutableSalesOverview;
import ch.zotteljedi.onlineshop.common.sale.dto.SalesItemOverview;
import ch.zotteljedi.onlineshop.common.sale.dto.SalesOverview;
import ch.zotteljedi.onlineshop.common.sale.service.SalesServiceLocal;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.core.product.service.ProductServiceImpl;
import ch.zotteljedi.onlineshop.core.purchase.mapper.PurchaseMapper;
import ch.zotteljedi.onlineshop.core.sale.mapper.SalesMapper;
import ch.zotteljedi.onlineshop.core.service.ApplicationService;
import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;
import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import ch.zotteljedi.onlineshop.data.entity.PurchaseEntity;
import ch.zotteljedi.onlineshop.data.entity.PurchaseItemEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Local(SalesServiceLocal.class)
@Transactional
public class SalesImpl extends ApplicationService implements SalesServiceLocal {

    @PersistenceContext(unitName = "ZotteltecPersistenceProvider")
    private EntityManager em;

    @Inject
    private CustomerServiceImpl customerService;

    @Inject
    private ProductServiceImpl productService;

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
        List<SalesItemOverview> salesItemOverview = getPurchaseByProduct(productEntity) .stream()
                .map(purchaseItemEntity -> SalesMapper.INSTANCE.map(purchaseItemEntity, customerService.buildCustomerRepresentation(purchaseItemEntity.getPurchase().getBuyer())))
                .collect(Collectors.toList());
        return SalesMapper.INSTANCE.map(productEntity, salesItemOverview);
    }

    private List<PurchaseItemEntity> getPurchaseByProduct(final ProductEntity productEntity) {
        return em.createNamedQuery("PurchaseItemEntity.getByProduct", PurchaseItemEntity.class)
                .setParameter("product", productEntity)
                .getResultList();
    }

}
