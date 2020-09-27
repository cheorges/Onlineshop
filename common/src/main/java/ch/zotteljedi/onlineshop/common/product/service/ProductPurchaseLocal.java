package ch.zotteljedi.onlineshop.common.product.service;

import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.product.dto.Purchase;

import javax.ejb.Local;
import java.io.Serializable;

@Local
public interface ProductPurchaseLocal extends Serializable {
    MessageContainer newPurchase(Purchase purchase);
}
