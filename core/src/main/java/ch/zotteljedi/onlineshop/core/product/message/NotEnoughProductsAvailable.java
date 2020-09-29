package ch.zotteljedi.onlineshop.core.product.message;

import ch.zotteljedi.onlineshop.common.message.Message;

public class NotEnoughProductsAvailable implements Message {

   private final Integer stock;

   public NotEnoughProductsAvailable(Integer stock) {
      this.stock = stock;
   }

   @Override
   public String getMessage() {
      return "Product stock '" + stock + "', there are not enouth products aviable.";
   }

}
