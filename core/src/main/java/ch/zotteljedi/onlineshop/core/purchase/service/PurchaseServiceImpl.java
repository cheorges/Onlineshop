package ch.zotteljedi.onlineshop.core.purchase.service;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.purchase.dto.PurchaseId;
import ch.zotteljedi.onlineshop.common.purchase.dto.PurchaseItemOverview;
import ch.zotteljedi.onlineshop.common.purchase.dto.PurchaseOverview;
import ch.zotteljedi.onlineshop.common.purchase.service.PurchaseServiceLocal;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.core.purchase.mapper.PurchaseMapper;
import ch.zotteljedi.onlineshop.core.service.ApplicationService;
import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;
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
@Local(PurchaseServiceLocal.class)
@Transactional
public class PurchaseServiceImpl extends ApplicationService implements PurchaseServiceLocal {

    @PersistenceContext(unitName = "ZotteltecPersistenceProvider")
    EntityManager em;

    @Inject
    CustomerServiceImpl customerService;

    @Override
    public List<PurchaseOverview> getPurchaseByCustomer(CustomerId customerId) {
        Optional<CustomerEntity> buyer = customerService.getCustomerEntityById(customerId);
        if (buyer.isEmpty()) {
            return Collections.emptyList();
        }

        List<PurchaseEntity> purchaseEntities = em.createNamedQuery("PurchaseEntitiy.getByBuyer", PurchaseEntity.class)
                .setParameter("buyer", buyer.get())
                .getResultList();

        return getPurchaseOverviews(purchaseEntities);
    }

    @Override
    public Optional<PurchaseOverview> getPurchaseById(PurchaseId purchaseId) {
        Optional<PurchaseEntity> purchaseEntity = em.createNamedQuery("PurchaseEntitiy.getById", PurchaseEntity.class)
                .setParameter("id", purchaseId.getValue())
                .getResultList().stream().findFirst();
        if (purchaseEntity.isEmpty()) {
            return Optional.empty();
        }
        return getPurchaseOverview(purchaseEntity.get());
    }

    private Optional<PurchaseOverview> getPurchaseOverview(PurchaseEntity purchase) {
        return getPurchaseOverviews(Collections.singletonList(purchase)).stream().findFirst();
    }

    private List<PurchaseOverview> getPurchaseOverviews(List<PurchaseEntity> purchaseEntities) {
        final List<PurchaseOverview> purchaseOverviews = new ArrayList<>();
        purchaseEntities.forEach(purchaseEntity -> {
            List<PurchaseItemOverview> items = getPurchaseItemEntity(purchaseEntity).stream()
                    .map(purchaseItemEntity -> PurchaseMapper.INSTANCE.map(purchaseItemEntity, customerService.buildCustomerRepresentation(purchaseItemEntity.getProduct().getSeller())))
                    .collect(Collectors.toList());
            purchaseOverviews.add(PurchaseMapper.INSTANCE.map(purchaseEntity, items));
        });
        return purchaseOverviews;
    }

    private List<PurchaseItemEntity> getPurchaseItemEntity(final PurchaseEntity purchaseEntity) {
        return em.createNamedQuery("PurchaseItemEntity.getByPruchase", PurchaseItemEntity.class)
                .setParameter("purchase", purchaseEntity)
                .getResultList();
    }

}
