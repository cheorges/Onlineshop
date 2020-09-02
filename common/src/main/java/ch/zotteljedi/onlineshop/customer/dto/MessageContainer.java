package ch.zotteljedi.onlineshop.customer.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MessageContainer implements Serializable {
    private final List<Message> messages = new ArrayList<>();

    public void add(Message message) {
        messages.add(message);
    }

    public boolean hasMessagesThenProvide(Consumer<Message> action) {
        if (messages.isEmpty()) {
            return false;
        }
        messages.forEach(action);
        messages.clear();
        return true;
    }
}
