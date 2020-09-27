package ch.zotteljedi.onlineshop.core.product.service;

import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.product.dto.Purchase;
import ch.zotteljedi.onlineshop.common.product.service.ProductPurchaseLocal;
import ch.zotteljedi.onlineshop.common.product.service.ProductServicLocal;
import ch.zotteljedi.onlineshop.core.service.ApplicationService;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

@Stateless
@Local(ProductServicLocal.class)
@Transactional
public class ProductPurchaseImpl extends ApplicationService implements ProductPurchaseLocal {

    @Override
    public MessageContainer newPurchase(Purchase purchase) {

        System.out.println(purchase);

        return getMessageContainer();
    }
}
