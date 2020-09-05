package ch.zotteljedi.onlineshop.customer.dto;

import java.io.Serializable;

public class PublicCustomer implements Serializable {
   private final CustomerId id;
   private String username;
   private String firstname;
   private String lastname;
   private String email;

   public PublicCustomer(CustomerId id) {
      this.id = id;
   }

   public CustomerId getId() {
      return id;
   }

   public String getRepresentation() {
      return this.firstname + " " + this.lastname;
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
