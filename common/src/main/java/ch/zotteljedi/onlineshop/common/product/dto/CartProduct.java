package ch.zotteljedi.onlineshop.common.product.dto;

import ch.zotteljedi.onlineshop.common.dto.ValueObject;
import org.immutables.value.Value;

@Value.Immutable
public interface CartProduct extends ValueObject {
    ProductId getProductId();
    Double getUnitprice();
    Integer getUnit();
}
