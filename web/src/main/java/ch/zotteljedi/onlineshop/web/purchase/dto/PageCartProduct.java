package ch.zotteljedi.onlineshop.web.purchase.dto;

import ch.zotteljedi.onlineshop.common.product.dto.ProductId;

import java.io.Serializable;

public class PageCartProduct implements Serializable {
    private final ProductId id;
    private final String title;
    private final Double unitprice;
    private final Integer stock;
    private Integer unit;

    public PageCartProduct(ProductId id, String title, Double unitprice, Integer stock) {
        this.id = id;
        this.title = title;
        this.unitprice = unitprice;
        this.stock = stock;
        this.unit = 1;
    }

    public ProductId getId() {
        return id;
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
        if (unit < stock) {
            this.unit++;
        }
    }

    public void decrementUnit() {
        if (unit > 0) {
            unit--;
        }
    }

    public Double getTotalPrice() {
        return getUnit() * getUnitprice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PageCartProduct))
            return false;
        PageCartProduct that = (PageCartProduct) o;
        return id.equals(that.id);
    }

}
