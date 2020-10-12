package ch.zotteljedi.onlineshop.web.sale.dto;

import java.time.LocalDate;

public class PageSalesItem {
    private String buyerRepresentation;
    private LocalDate boughtAt;
    private Integer unit;
    private Double unitprice;

    public String getBuyerRepresentation() {
        return buyerRepresentation;
    }

    public void setBuyerRepresentation(String buyerRepresentation) {
        this.buyerRepresentation = buyerRepresentation;
    }

    public LocalDate getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(LocalDate boughtAt) {
        this.boughtAt = boughtAt;
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
        return getUnit() * getUnitprice();
    }
}
