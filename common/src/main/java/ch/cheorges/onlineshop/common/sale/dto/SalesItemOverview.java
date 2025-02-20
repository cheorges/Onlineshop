package ch.cheorges.onlineshop.common.sale.dto;

import ch.cheorges.onlineshop.common.dto.ValueObject;
import org.immutables.value.Value;

import java.time.LocalDate;

@Value.Immutable
public interface SalesItemOverview extends ValueObject {
    String getBuyerRepresentation();

    LocalDate getBoughtAt();

    Integer getUnit();

    Double getUnitprice();
}
