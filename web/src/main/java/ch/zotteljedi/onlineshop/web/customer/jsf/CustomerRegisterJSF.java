package ch.zotteljedi.onlineshop.web.customer.jsf;


import ch.zotteljedi.onlineshop.web.customer.dto.PublicCustomer;
import ch.zotteljedi.onlineshop.common.customer.service.CustomerServiceLocal;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class CustomerRegisterJSF implements Serializable {

    @Inject
    private CustomerServiceLocal customerServiceLocal;

    private PublicCustomer newCustomer;

    public String register() {
        return "login";
    }
}
