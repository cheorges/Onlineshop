package ch.zotteljedi.onlineshop.common.purchase.dto;

import ch.zotteljedi.onlineshop.common.dto.ValueObject;
import org.immutables.value.Value;

@Value.Immutable
public interface PurchaseItemOverview extends ValueObject {
    String getTitle();

    Integer getUnit();

    Double getUnitprice();

    String getSellerRepresentation();
}
