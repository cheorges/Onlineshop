package ch.zotteljedi.onlineshop.common.product.dto;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.dto.Entity;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

@Value.Immutable
public interface ChangeProduct extends Entity<ProductId> {
    String getTitle();
    @Nullable
    String getDescription();
    Double getUnitprice();
    Integer getStock();
    byte[] getPhoto();
    CustomerId getSellerId();
}
