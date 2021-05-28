package ch.cheorges.onlineshop.common.purchase.service;

import ch.cheorges.onlineshop.common.customer.dto.CustomerId;
import ch.cheorges.onlineshop.common.purchase.dto.PurchaseId;
import ch.cheorges.onlineshop.common.purchase.dto.PurchaseOverview;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Local
public interface PurchaseServiceLocal extends Serializable {
    List<PurchaseOverview> getPurchaseByCustomer(CustomerId customerId);

    Optional<PurchaseOverview> getPurchaseById(PurchaseId purchaseId);
}
