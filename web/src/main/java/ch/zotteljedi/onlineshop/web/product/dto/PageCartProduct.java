package ch.zotteljedi.onlineshop.web.product.dto;

import ch.zotteljedi.onlineshop.common.product.dto.ProductId;

import java.io.Serializable;

public class PageCartProduct implements Serializable {
    private final ProductId productId;
    private final String title;
    private final Double unitprice;
    private Integer unit;

    public PageCartProduct(ProductId productId, String title, Double unitprice) {
        this.productId = productId;
        this.title = title;
        this.unitprice = unitprice;
        this.unit = 1;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public void incrementUnit() {
        this.unit++;
    }

    public Double getTotalPrice() {
        return getUnit() * getUnitprice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageCartProduct)) return false;
        PageCartProduct that = (PageCartProduct) o;
        return productId.equals(that.productId);
    }

}
