package ch.zotteljedi.onlineshop.web.customer.dto;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;

public class PersistPageCustomer extends PageCustomer {
   private final CustomerId id;

   public PersistPageCustomer(CustomerId id) {
      this.id = id;
   }

   public CustomerId getId() {
      return id;
   }

   public String getRepresentation() {
      return getFirstname() + " " + getLastname();
   }
}
