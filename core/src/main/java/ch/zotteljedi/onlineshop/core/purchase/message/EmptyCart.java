package ch.zotteljedi.onlineshop.core.purchase.message;

import ch.zotteljedi.onlineshop.common.message.Message;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;

public class EmptyCart implements Message {

    @Override
    public String getMessage() {
        return "No products in the shoppingcart.";
    }
}
