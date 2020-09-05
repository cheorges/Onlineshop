package ch.zotteljedi.onlineshop.helper;

import ch.zotteljedi.onlineshop.common.dto.Message;
import ch.zotteljedi.onlineshop.common.dto.MessageContainer;

public abstract class ApplicationService {
    private final MessageContainer messageContainer = new MessageContainer();

    protected void addMessage(Message message) {
        messageContainer.add(message);
    }

    public MessageContainer getMessageContainer() {
        return messageContainer;
    }
}
