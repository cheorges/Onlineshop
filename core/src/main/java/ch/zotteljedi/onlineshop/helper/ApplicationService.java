package ch.zotteljedi.onlineshop.helper;

import ch.zotteljedi.onlineshop.common.message.Message;
import ch.zotteljedi.onlineshop.common.message.MessageContainer;

public abstract class ApplicationService {
    private final MessageContainer messageContainer = new MessageContainer();

    protected void addMessage(Message message) {
        messageContainer.add(message);
    }

    public MessageContainer getMessageContainer() {
        return messageContainer;
    }
}
