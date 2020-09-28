package ch.zotteljedi.onlineshop.common.message;

import ch.zotteljedi.onlineshop.common.dto.ValueObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MessageContainer implements ValueObject {
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

   public void hasNoMessage(Runnable action) {
        if (messages.isEmpty()) {
            action.run();
        }
   }
}
