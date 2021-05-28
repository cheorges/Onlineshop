package ch.cheorges.onlineshop.common.purchase.dto;

import ch.cheorges.onlineshop.common.dto.ValueObject;
import org.immutables.value.Value;

@Value.Immutable
public interface PurchaseItemOverview extends ValueObject {
    String getTitle();

    Integer getUnit();

    Double getUnitprice();

    String getSellerRepresentation();
}
