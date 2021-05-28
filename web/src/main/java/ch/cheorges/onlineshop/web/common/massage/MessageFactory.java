package ch.cheorges.onlineshop.web.common.massage;

import ch.cheorges.onlineshop.common.message.Message;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class MessageFactory implements Serializable {

    private static final String NO_DETAIL = "";

    public void showInfo(String message) {
        showMessage(() -> message, FacesMessage.SEVERITY_INFO);
    }

    public void showInfo(Message message) {
        showMessage(message, FacesMessage.SEVERITY_INFO);
    }

    public void showError(String message) {
        showMessage(() -> message, FacesMessage.SEVERITY_ERROR);
    }

    public void showError(Message message) {
        showMessage(message, FacesMessage.SEVERITY_ERROR);
    }

    private void showMessage(Message message, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message.getMessage(), NO_DETAIL));
    }
}
