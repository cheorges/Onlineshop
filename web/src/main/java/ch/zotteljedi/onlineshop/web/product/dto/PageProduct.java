package ch.zotteljedi.onlineshop.web.product.dto;

import ch.zotteljedi.onlineshop.web.customer.dto.PageCustomer;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PageProduct implements Serializable {
   private String title;
   private String description;
   private Double unitprice;
   private Integer stock;
   private PageCustomer seller;

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Double getUnitprice() {
      return unitprice;
   }

   public void setUnitprice(Double unitprice) {
      this.unitprice = unitprice;
   }

   public Integer getStock() {
      return stock;
   }

   public void setStock(Integer stock) {
      this.stock = stock;
   }

   public PageCustomer getSeller() {
      return seller;
   }

   public void setSeller(PageCustomer seller) {
      this.seller = seller;
   }

}
