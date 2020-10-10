package ch.zotteljedi.onlineshop.web.purchase.dto;

import java.io.Serializable;

public class PagePurchaseItem implements Serializable {
    private String title;
    private Integer unit;
    private Double unitprice;
    private String sellerRepresentation;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSellerRepresentation() {
        return sellerRepresentation;
    }

    public void setSellerRepresentation(String sellerRepresentation) {
        this.sellerRepresentation = sellerRepresentation;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public Double getTotalPrice() {
        return getUnitprice() * getUnit();
    }
}
