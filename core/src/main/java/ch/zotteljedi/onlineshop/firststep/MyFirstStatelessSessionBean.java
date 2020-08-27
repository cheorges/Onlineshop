package ch.zotteljedi.onlineshop.firststep;

import javax.ejb.Stateless;

@Stateless(name = "MyFirstStatelessSessionBeanEJB")
public class MyFirstStatelessSessionBean implements MyFirstStatelessSessionBeanLocal {
    public MyFirstStatelessSessionBean() {
    }

    @Override
    public String getMessage() {
        return "Hello from EJB";
    }
}