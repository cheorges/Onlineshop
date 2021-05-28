package ch.cheorges.onlineshop.common.product.dto;

import ch.cheorges.onlineshop.common.dto.Entity;
import ch.cheorges.onlineshop.common.customer.dto.Customer;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

@Value.Immutable
public interface Product extends Entity<ProductId> {
    String getTitle();

    @Nullable
    String getDescription();

    Double getUnitprice();

    Integer getStock();

    byte[] getPhoto();

    Customer getSeller();
}
