package ch.zotteljedi.onlineshop.web.customer.jsf;

import ch.zotteljedi.onlineshop.common.customer.service.CustomerServiceLocal;
import ch.zotteljedi.onlineshop.common.message.Message;
import ch.zotteljedi.onlineshop.web.customer.dto.PageCustomer;
import ch.zotteljedi.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.web.helper.Hash256;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

   public void changeUsername(String username) throws UnauthorizedAccessException {
      if (!customerServiceLocal.changeCustomerUsername(customerSessionJSF.getCustomerId(), username)
            .hasMessagesThenProvide(msg -> showMessage("customerUsernameChangeForm", msg))) {
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
         PageCustomer customer = customerSessionJSF.getCustomer();
         if (customerServiceLocal.checkCredentials(customer.getUsername(), Hash256.INSTANCE.hash256(oldPassword))) {
            customerServiceLocal.changeCustomerPassword(customer.getId(), Hash256.INSTANCE.hash256(newPassword));
            showMessage(null, () -> "Password changed.");
         } else {
            showMessage("customerChangePassordForm", () -> "Invalid credentials.");
         }
      } catch (NoSuchAlgorithmException e) {
         Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
      }
   }

   public String deleteCustomer(String password) throws UnauthorizedAccessException {
      try {
         PageCustomer customer = customerSessionJSF.getCustomer();
         if (customerServiceLocal.checkCredentials(customer.getUsername(), Hash256.INSTANCE.hash256(password))) {
            customerServiceLocal.deleteCustomer(customer.getId());
            showMessage(null, () -> "Customer profile successfully deleted.");
            return customerSessionJSF.logout();
         } else {
            showMessage("customerChangePassordForm", () -> "Invalid credentials.");
         }
      } catch (NoSuchAlgorithmException e) {
         Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
      }
      return "custumer_profile";
   }

   private void showMessage(final String clientId, final Message message) {
      FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(message.getMessage()));
   }
}
