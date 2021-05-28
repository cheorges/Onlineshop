package ch.cheorges.onlineshop.common.product.service;

import ch.cheorges.onlineshop.common.message.MessageContainer;
import ch.cheorges.onlineshop.common.product.dto.ChangeProduct;
import ch.cheorges.onlineshop.common.product.dto.NewProduct;
import ch.cheorges.onlineshop.common.product.dto.Product;
import ch.cheorges.onlineshop.common.product.dto.ProductId;
import ch.cheorges.onlineshop.common.customer.dto.CustomerId;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Local
public interface ProductServiceLocal extends Serializable {
    List<Product> getProductsBySeller(CustomerId customerId);

    List<Product> getAllAvailableProducts();

    Optional<Product> getProductById(ProductId id);

    MessageContainer addNewProduct(NewProduct product);

    MessageContainer changeProduct(ChangeProduct product);

    MessageContainer deleteProduct(ProductId id);
}
