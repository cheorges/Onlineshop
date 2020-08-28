package ch.zotteljedi.onlineshop.customer.services;

import java.io.Serializable;
import javax.ejb.Local;

@Local
public interface CustomerSessionServicesLocal extends Serializable {
   boolean checkCredantioals(String username, String password);
}
