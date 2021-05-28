package ch.cheorges.onlineshop.core.builder;

import ch.cheorges.onlineshop.data.entity.CustomerEntity;
import ch.cheorges.onlineshop.data.entity.PurchaseEntity;

import java.time.LocalDate;

public class PurchaseEntityBuilder {
    private final PurchaseEntity purchaseEntity = new PurchaseEntity();

    public PurchaseEntityBuilder id(Integer id) {
        purchaseEntity.setId(id);
        return this;
    }

    public PurchaseEntityBuilder boughtAt(LocalDate boughtAt) {
        purchaseEntity.setBoughtAt(boughtAt);
        return this;
    }

    public PurchaseEntityBuilder buyer(CustomerEntity buyer) {
        purchaseEntity.setBuyer(buyer);
        return this;
    }

    public PurchaseEntity build() {
        return purchaseEntity;
    }
}
