package ch.cheorges.onlineshop.common.sale.dto;

import ch.cheorges.onlineshop.common.dto.Entity;
import ch.cheorges.onlineshop.common.product.dto.ProductId;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface SalesOverview extends Entity<ProductId> {
    String getTitle();

    List<SalesItemOverview> getSalesItems();
}
