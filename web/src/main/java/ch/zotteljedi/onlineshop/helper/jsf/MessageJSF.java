package ch.zotteljedi.onlineshop.helper.jsf;

import jdk.jfr.Name;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class MessageJSF  implements Serializable {

    public String getRequiredMassage() {
        return "Field is required.";
    }

    public String getEmailMessage() {
        return "Enter an E-Mail address.";
    }
}
