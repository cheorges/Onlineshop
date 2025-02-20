package ch.cheorges.onlineshop.core.sale.service;

import ch.cheorges.onlineshop.common.customer.dto.CustomerId;
import ch.cheorges.onlineshop.common.product.dto.ProductId;
import ch.cheorges.onlineshop.common.sale.dto.SalesItemOverview;
import ch.cheorges.onlineshop.common.sale.dto.SalesOverview;
import ch.cheorges.onlineshop.common.sale.service.SalesServiceLocal;
import ch.cheorges.onlineshop.core.sale.mapper.SalesMapper;
import ch.cheorges.onlineshop.core.service.ApplicationService;
import ch.cheorges.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.cheorges.onlineshop.core.product.service.ProductServiceImpl;
import ch.cheorges.onlineshop.data.entity.ProductEntity;
import ch.cheorges.onlineshop.data.entity.PurchaseItemEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Local(SalesServiceLocal.class)
@Transactional
public class SalesServiceImpl extends ApplicationService implements SalesServiceLocal {

    @PersistenceContext(unitName = "CheorgesTecPersistenceProvider")
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
