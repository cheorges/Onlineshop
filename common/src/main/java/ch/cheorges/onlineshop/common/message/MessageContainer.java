package ch.cheorges.onlineshop.common.message;

import ch.cheorges.onlineshop.common.dto.ValueObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MessageContainer implements ValueObject {
    private final List<Message> messages = new ArrayList<>();

    public void add(Message message) {
        messages.add(message);
    }

    public boolean hasMessagesThenProvide(Consumer<Message> action) {
        if (!hasMessages()) {
            return false;
        }
        messages.forEach(action);
        messages.clear();
        return true;
    }

    public void hasNoMessageThenProvide(Runnable action) {
        if (!hasMessages()) {
            action.run();
        }
    }

    public boolean hasMessages() {
        return !messages.isEmpty();
    }

    public List<Message> getMessages() {
        return messages;
    }
}
