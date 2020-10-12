package ch.zotteljedi.onlineshop.common.sale.dto;

import ch.zotteljedi.onlineshop.common.dto.Entity;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface SalesOverview extends Entity<ProductId> {
    String getTitle();

    List<SalesItemOverview> getSalesItems();
}
