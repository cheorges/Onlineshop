package ch.zotteljedi.onlineshop.common.purchase.service;

import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.purchase.dto.Purchase;

import javax.ejb.Local;
import java.io.Serializable;

@Local
public interface ProductPurchaseServiceLocal extends Serializable {
    MessageContainer newPurchase(Purchase purchase);
}
