package ch.zotteljedi.onlineshop.customer.jsf;

import ch.zotteljedi.onlineshop.customer.dto.*;
import ch.zotteljedi.onlineshop.common.dto.Message;
import ch.zotteljedi.onlineshop.customer.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.customer.service.CustomerServiceLocal;
import ch.zotteljedi.onlineshop.helper.Hash256;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class CustomerJSF implements Serializable {

   @Inject
   private CustomerServiceLocal customerServiceLocal;

   @Inject
   private CustomerSessionJSF customerSessionJSF;

   public void addNewCustomer(String username, String firstname, String lastname, String email, String password) {
      try {
         NewCustomer newCustomer = ImmutableNewCustomer.builder()
               .username(username)
               .firstname(firstname)
               .lastname(lastname)
               .email(email)
               .password(Hash256.INSTANCE.hash256(password))
               .build();
         if (!customerServiceLocal.addNewCustomer(newCustomer)
               .hasMessagesThenProvide(msg -> showMessage("customerRegisterForm", msg))) {
            showMessage(null, () -> "Registration successful.");
         }
      } catch (NoSuchAlgorithmException e) {
         showMessage("customerRegisterForm", () -> "No hash value of the password could be generated. No profile was created.");
      }
   }

   public void changeUsername(String username) throws UnauthorizedAccessException {
      if (!customerServiceLocal.changeCustomerUsername(customerSessionJSF.getCustomerId(), username)
            .hasMessagesThenProvide((msg) -> showMessage("customerUsernameChangeForm", msg))) {
         customerSessionJSF.update();
         showMessage(null, () -> "Username changed.");
      }
   }

   public void changeCustomer(String firstname, String lastname, String email) throws UnauthorizedAccessException {
      customerServiceLocal.changeCustomer(customerSessionJSF.getCustomerId(), firstname, lastname, email);
      customerSessionJSF.update();
      showMessage(null, () -> "Personal details changed.");
   }

   public void changePassword(String oldPassword, String newPassword) throws UnauthorizedAccessException {
      try {
         PublicCustomer customer = customerSessionJSF.getCustomer();
         if (customerServiceLocal.checkCredentials(customer.getUsername(), Hash256.INSTANCE.hash256(oldPassword))) {
            customerServiceLocal.changeCustomerPassword(customer.getId(), Hash256.INSTANCE.hash256(newPassword));
            showMessage(null, () -> "Password changed.");
         } else {
            showMessage("customerChangePassordForm", () -> "Invalid credentials.");
         }
      } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
   }

   public void deleteCustomer(String password) throws UnauthorizedAccessException {
      try {
         PublicCustomer customer = customerSessionJSF.getCustomer();
         if (customerServiceLocal.checkCredentials(customer.getUsername(), Hash256.INSTANCE.hash256(password))) {
            customerServiceLocal.deleteCustomer(customer.getId());
            showMessage(null, () -> "Customer profile successfully deleted.");
            customerSessionJSF.logout();
         } else {
            showMessage("customerChangePassordForm", () -> "Invalid credentials.");
         }
      } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
   }

   private void showMessage(final String clientId, final Message message) {
      FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(message.getMessage()));
   }
}
