package ch.cheorges.onlineshop.common.purchase.service;

import ch.cheorges.onlineshop.common.purchase.dto.Purchase;
import ch.cheorges.onlineshop.common.message.MessageContainer;

import javax.ejb.Local;
import java.io.Serializable;

@Local
public interface ProductPurchaseServiceLocal extends Serializable {
    MessageContainer newPurchase(Purchase purchase);
}
