package ch.zotteljedi.onlineshop.common.product.dto;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.dto.ValueObject;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface Purchase extends ValueObject {
    CustomerId getBuyerId();
    List<CartProduct> getCartProduct();
}
