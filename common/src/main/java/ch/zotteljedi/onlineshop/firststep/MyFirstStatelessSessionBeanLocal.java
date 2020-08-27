package ch.zotteljedi.onlineshop.firststep;

import javax.ejb.Local;

@Local
public interface MyFirstStatelessSessionBeanLocal {
    String getMessage();
}
