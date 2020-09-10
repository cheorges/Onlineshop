package ch.zotteljedi.onlineshop.web.product.dto;

import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.web.customer.dto.PageCustomer;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class PageProduct implements Serializable {
   private String title;
   private String description;
   private Double price;
   private PageCustomer seller;

   private final NumberFormat formatter = NumberFormat.getInstance();

   public PageProduct() {
      formatter.setMinimumFractionDigits(2);
      formatter.setMaximumFractionDigits(2);
   }

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

   public Double getPrice() {
      return price;
   }

   public void setPrice(Double price) {
      this.price = price;
   }

   public PageCustomer getSeller() {
      return seller;
   }

   public void setSeller(PageCustomer seller) {
      this.seller = seller;
   }

}
