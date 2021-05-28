package ch.cheorges.onlineshop.web.sale.dto;

import ch.cheorges.onlineshop.common.product.dto.ProductId;

import java.io.Serializable;
import java.util.List;

public class PageSales implements Serializable {
    private ProductId productId;
    private String title;
    private List<PageSalesItem> items;

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PageSalesItem> getItems() {
        return items;
    }

    public void setItems(List<PageSalesItem> items) {
        this.items = items;
    }

    public Integer getTotalUnits() {
        return getItems().stream().mapToInt(PageSalesItem::getUnit).sum();
    }

    public Double getTotalAmount() {
        return getItems().stream().mapToDouble(PageSalesItem::getTotalPrice).sum();
    }
}
