package ch.zotteljedi.onlineshop.web.purchase.dto;

import ch.zotteljedi.onlineshop.common.purchase.dto.PurchaseId;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class PagePurchase implements Serializable {
    private PurchaseId purchaseId;
    private LocalDate boughtAt;
    private List<PagePurchaseItem> items;

    public PurchaseId getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(PurchaseId purchaseId) {
        this.purchaseId = purchaseId;
    }

    public LocalDate getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(LocalDate boughtAt) {
        this.boughtAt = boughtAt;
    }

    public List<PagePurchaseItem> getItems() {
        return items;
    }

    public void setItems(List<PagePurchaseItem> items) {
        this.items = items;
    }

    public Double getTotalAmount() {
        return getItems().stream().mapToDouble(PagePurchaseItem::getTotalPrice).sum();
    }
}
