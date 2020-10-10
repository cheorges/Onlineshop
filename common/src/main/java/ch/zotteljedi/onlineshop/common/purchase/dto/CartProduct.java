package ch.zotteljedi.onlineshop.common.purchase.dto;

import ch.zotteljedi.onlineshop.common.dto.ValueObject;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import org.immutables.value.Value;

@Value.Immutable
public interface CartProduct extends ValueObject {
    ProductId getProductId();

    Double getUnitprice();

    Integer getUnit();
}
