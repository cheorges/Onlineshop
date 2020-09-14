package ch.zotteljedi.onlineshop.common.product.dto;

import ch.zotteljedi.onlineshop.common.dto.ValueObject;
import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

@Value.Immutable
public interface NewProduct extends ValueObject {
    String getTitle();
    @Nullable
    String getDescription();
    Double getUnitprice();
    Integer getStock();
    byte[] getPhoto();
    CustomerId getSellerId();
}
