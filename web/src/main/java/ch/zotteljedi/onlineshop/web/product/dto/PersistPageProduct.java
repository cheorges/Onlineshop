package ch.zotteljedi.onlineshop.web.product.dto;

import ch.zotteljedi.onlineshop.common.product.dto.ProductId;

public class PersistPageProduct extends PageProduct {
    private final ProductId id;

    public PersistPageProduct(ProductId id) {
        this.id = id;
    }

    public ProductId getId() {
        return id;
    }

}
