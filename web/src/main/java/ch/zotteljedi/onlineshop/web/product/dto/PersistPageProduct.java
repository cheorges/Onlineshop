package ch.zotteljedi.onlineshop.web.product.dto;

import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.web.customer.dto.PageCustomer;

import java.io.Serializable;

public class PersistPageProduct extends PageProduct {
   private final ProductId id;

   public PersistPageProduct(ProductId id) {
      this.id = id;
   }

   public ProductId getId() {
      return id;
   }

}
