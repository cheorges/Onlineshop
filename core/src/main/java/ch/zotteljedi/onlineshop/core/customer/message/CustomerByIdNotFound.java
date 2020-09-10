package ch.zotteljedi.onlineshop.core.customer.message;

import ch.zotteljedi.onlineshop.common.message.Message;
import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;

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
