package ch.cheorges.onlineshop.common.purchase.dto;

import ch.cheorges.onlineshop.common.dto.ValueObject;
import ch.cheorges.onlineshop.common.product.dto.ProductId;
import org.immutables.value.Value;

@Value.Immutable
public interface CartProduct extends ValueObject {
    ProductId getProductId();

    Double getUnitprice();

    Integer getUnit();
}
