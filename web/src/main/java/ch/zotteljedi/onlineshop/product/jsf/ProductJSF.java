package ch.zotteljedi.onlineshop.product.jsf;

import ch.zotteljedi.onlineshop.customer.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.customer.jsf.CustomerJSF;
import ch.zotteljedi.onlineshop.customer.jsf.CustomerSessionJSF;
import ch.zotteljedi.onlineshop.product.dto.ImmutableNewProduct;
import ch.zotteljedi.onlineshop.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.product.dto.Product;
import ch.zotteljedi.onlineshop.product.service.ProductServicLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class ProductJSF implements Serializable {

    @Inject
    ProductServicLocal productServicLocal;

    @Inject
    CustomerSessionJSF customerSessionJSF;

    public List<Product> getProductsBySeller() throws UnauthorizedAccessException {
        return productServicLocal.getProductsBySeller(customerSessionJSF.getCustomerId());
    }

    public void saleNewProduct(String title, String description, Double price, Part photo) {
        try {
            InputStream inputStream = photo.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[10240];
            for (int length = 0; (length = inputStream.read(buffer)) >0;) {
                outputStream.write(buffer, 0, length);
            }
            NewProduct product = ImmutableNewProduct.builder()
                    .title(title)
                    .description(description)
                    .price(price)
                    .photo(outputStream.toByteArray())
                    .customerId(customerSessionJSF.getCustomerId())
                    .build();
            productServicLocal.addNewProduct(product);
        } catch (IOException | UnauthorizedAccessException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }
    }

}
