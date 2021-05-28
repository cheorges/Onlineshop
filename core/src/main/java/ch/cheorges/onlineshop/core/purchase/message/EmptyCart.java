package ch.cheorges.onlineshop.core.purchase.message;

import ch.cheorges.onlineshop.common.message.Message;

public class EmptyCart implements Message {

    @Override
    public String getMessage() {
        return "No products in the shoppingcart.";
    }
}
