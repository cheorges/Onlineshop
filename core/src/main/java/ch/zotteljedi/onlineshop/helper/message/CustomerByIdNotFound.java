package ch.zotteljedi.onlineshop.helper.message;

import ch.zotteljedi.onlineshop.common.dto.Message;
import ch.zotteljedi.onlineshop.customer.dto.CustomerId;

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
