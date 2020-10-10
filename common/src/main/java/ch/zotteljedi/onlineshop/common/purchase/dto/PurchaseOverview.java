package ch.zotteljedi.onlineshop.common.purchase.dto;

import ch.zotteljedi.onlineshop.common.dto.Entity;
import org.immutables.value.Value;

import java.time.LocalDate;
import java.util.List;

@Value.Immutable
public interface PurchaseOverview extends Entity<PurchaseId> {
    LocalDate getBoughtAt();

    List<PurchaseItemOverview> getPurchaseItems();
}
