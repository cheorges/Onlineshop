package ch.cheorges.onlineshop.core.customer.message;

import ch.cheorges.onlineshop.common.customer.dto.CustomerId;
import ch.cheorges.onlineshop.common.message.Message;

public class CustomerByIdNotFound implements Message {

    private final CustomerId customerId;

    public CustomerByIdNotFound(CustomerId customerId) {
        this.customerId = customerId;
    }

    @Override
    public String getMessage() {
        return "Customer by ID '" + customerId.getValue() + "' not found.";
    }
}
