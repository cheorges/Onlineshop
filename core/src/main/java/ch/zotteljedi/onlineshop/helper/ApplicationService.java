package ch.zotteljedi.onlineshop.helper;

import ch.zotteljedi.onlineshop.customer.dto.Message;
import ch.zotteljedi.onlineshop.customer.dto.MessageContainer;

public abstract class ApplicationService {
    private final MessageContainer messageContainer = new MessageContainer();

    protected void addMessage(Message message) {
        messageContainer.add(message);
    }

    public MessageContainer getMessageContainer() {
        return messageContainer;
    }
}
