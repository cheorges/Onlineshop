package ch.cheorges.onlineshop.web.customer.dto;

import ch.cheorges.onlineshop.common.customer.dto.CustomerId;

import java.io.Serializable;

public class PageCustomer implements Serializable {
   private final CustomerId id;
   private String username;
   private String firstname;
   private String lastname;
   private String email;

   public PageCustomer(CustomerId id) {
      this.id = id;
   }

   public String getRepresentation() {
      return getFirstname() + " " + getLastname();
   }

   public CustomerId getId() {
      return id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getFirstname() {
      return firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

}
