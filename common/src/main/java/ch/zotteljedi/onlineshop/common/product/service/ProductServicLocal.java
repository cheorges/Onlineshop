package ch.zotteljedi.onlineshop.common.product.service;

import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.product.dto.ChangeProduct;
import ch.zotteljedi.onlineshop.common.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.common.product.dto.Product;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Local
public interface ProductServicLocal extends Serializable {
    List<Product> getProductsBySeller(CustomerId id);
    List<Product> getAllAvailableProducts();
    Optional<Product> getProductById(ProductId id);
    MessageContainer addNewProduct(NewProduct product);
    MessageContainer changeProduct(ChangeProduct product);
    MessageContainer deleteProduct(ProductId id);
}
