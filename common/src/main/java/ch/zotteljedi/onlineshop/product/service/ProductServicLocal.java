package ch.zotteljedi.onlineshop.product.service;

import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.product.dto.Product;
import ch.zotteljedi.onlineshop.product.dto.ProductId;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Local
public interface ProductServicLocal extends Serializable {
    List<Product> getProductsBySeller(CustomerId id);
    List<Product> getAllProducts();
    Optional<Product> getProductById(ProductId id);
    MessageContainer addNewProduct(NewProduct product);
}
