package ch.zotteljedi.onlineshop.core.product.service;

import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.product.dto.Purchase;
import ch.zotteljedi.onlineshop.common.product.service.ProductPurchaseLocal;
import ch.zotteljedi.onlineshop.common.product.service.ProductServicLocal;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.core.service.ApplicationService;
import ch.zotteljedi.onlineshop.data.entity.PurchaseEntitiy;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Stateless
@Local(ProductServicLocal.class)
@Transactional
public class ProductPurchaseImpl extends ApplicationService implements ProductPurchaseLocal {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CustomerServiceImpl customerService;

    @Override
    public MessageContainer newPurchase(Purchase purchase) {
        PurchaseEntitiy purchaseEntitiy = new PurchaseEntitiy();
        purchaseEntitiy.setBoughtAt(LocalDate.now());
        purchaseEntitiy.setBuyer(customerService.getCustomerEntityById(purchase.getBuyerId()));
        em.persist(purchaseEntitiy);
        return getMessageContainer();
    }
}
