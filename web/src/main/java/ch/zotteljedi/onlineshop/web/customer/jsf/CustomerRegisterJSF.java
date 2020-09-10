package ch.zotteljedi.onlineshop.web.customer.jsf;


import ch.zotteljedi.onlineshop.common.customer.dto.ImmutableNewCustomer;
import ch.zotteljedi.onlineshop.common.customer.dto.NewCustomer;
import ch.zotteljedi.onlineshop.common.customer.service.CustomerServiceLocal;
import ch.zotteljedi.onlineshop.web.common.Hash256;
import ch.zotteljedi.onlineshop.web.common.massage.MessageFactory;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class CustomerRegisterJSF implements Serializable {

   @Inject
   private CustomerServiceLocal customerServiceLocal;

   @Inject
   private MessageFactory messageFactory;

   public String register(String username, String firstname, String lastname, String email, String password) {
      try {
         NewCustomer newCustomer = ImmutableNewCustomer.builder()
               .username(username)
               .firstname(firstname)
               .lastname(lastname)
               .email(email)
               .password(Hash256.INSTANCE.hash256(password))
               .build();
         if (!customerServiceLocal.addNewCustomer(newCustomer)
               .hasMessagesThenProvide(msg -> messageFactory.showError(msg))) {
            messageFactory.showInfo("Registration successful.");
            return "login";
         }
      } catch (NoSuchAlgorithmException e) {
         messageFactory.showError("No hash value of the password could be generated. No profile was created.");
      }
      return "register";
   }

}
