package ch.cheorges.onlineshop.common.sale.service;

import ch.cheorges.onlineshop.common.sale.dto.SalesOverview;
import ch.cheorges.onlineshop.common.customer.dto.CustomerId;
import ch.cheorges.onlineshop.common.product.dto.ProductId;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Local
public interface SalesServiceLocal extends Serializable {
    List<SalesOverview> getSalesByCustomer(CustomerId customerId);

    Optional<SalesOverview> getSalesByProductId(ProductId purchaseId);
}
