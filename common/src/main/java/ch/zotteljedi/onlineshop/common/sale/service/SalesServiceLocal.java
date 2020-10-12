package ch.zotteljedi.onlineshop.common.sale.service;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.common.sale.dto.SalesOverview;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Local
public interface SalesServiceLocal extends Serializable {
    List<SalesOverview> getSalesByCustomer(CustomerId customerId);

    Optional<SalesOverview> getSalesByProductId(ProductId purchaseId);
}
