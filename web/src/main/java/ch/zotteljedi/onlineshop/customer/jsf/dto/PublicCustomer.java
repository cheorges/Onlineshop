package ch.zotteljedi.onlineshop.customer.jsf.dto;

import java.io.Serializable;

public class PublicCustomer implements Serializable {
   private final Integer id;
   private String username;
   private String firstname;
   private String lastname;
   private String email;

   public PublicCustomer(Integer id) {
      this.id = id;
   }

   public Integer getId() {
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
