package ch.cheorges.onlineshop.core.product.message;

import ch.cheorges.onlineshop.common.message.Message;
import ch.cheorges.onlineshop.common.product.dto.ProductId;

public class ProductByIdNotFound implements Message {

    private final ProductId productId;

    public ProductByIdNotFound(ProductId productId) {
        this.productId = productId;
    }

    @Override
    public String getMessage() {
        return "Product by ID '" + productId.getValue() + "' not found.";
    }
}
