package ch.zotteljedi.onlineshop.product.service;

import ch.zotteljedi.onlineshop.common.dto.MessageContainer;
import ch.zotteljedi.onlineshop.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.product.dto.Product;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.List;

@Local
public interface ProductServicLocal extends Serializable {
    List<Product> getProductsBySeller(CustomerId id);
    MessageContainer addNewProduct(NewProduct product);
}
