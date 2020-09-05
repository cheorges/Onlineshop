package ch.zotteljedi.onlineshop.product.dto;

import ch.zotteljedi.onlineshop.common.dto.ValueObject;
import ch.zotteljedi.onlineshop.customer.dto.CustomerId;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

@Value.Immutable
public interface NewProduct extends ValueObject {
    String getTitle();
    @Nullable
    String getDescription();
    Double getPrice();
    byte[] getPhoto();
    CustomerId getCustomerId();
}
