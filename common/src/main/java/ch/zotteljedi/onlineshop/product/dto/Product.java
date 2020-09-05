package ch.zotteljedi.onlineshop.product.dto;

import ch.zotteljedi.onlineshop.common.dto.Entity;
import ch.zotteljedi.onlineshop.customer.dto.Customer;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

@Value.Immutable
public interface Product extends Entity<ProductId> {
    String getTitle();
    @Nullable
    String getDescription();
    Double getPrice();
    byte[] getPhoto();
    Customer getSeller();
}
