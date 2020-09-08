package ch.zotteljedi.onlineshop.web.product.jsf;

import ch.zotteljedi.onlineshop.common.message.Message;
import ch.zotteljedi.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.web.customer.jsf.CustomerJSF;
import ch.zotteljedi.onlineshop.web.customer.jsf.CustomerSessionJSF;
import ch.zotteljedi.onlineshop.web.helper.Photo;
import ch.zotteljedi.onlineshop.common.product.dto.ImmutableNewProduct;
import ch.zotteljedi.onlineshop.common.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.common.product.dto.Product;
import ch.zotteljedi.onlineshop.common.product.service.ProductServicLocal;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
                    .photo(Photo.scale(outputStream.toByteArray()))
                    .customerId(customerSessionJSF.getCustomerId())
                    .build();
            productServicLocal.addNewProduct(product);
            showMessage(null, () -> "Product created successful.");
        } catch (IOException | UnauthorizedAccessException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }
    }

    public void changeProduct() {

    }

    public List<Product> getAll() {
        return productServicLocal.getAllProducts();
    }

    private void showMessage(final String clientId, final Message message) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(message.getMessage()));
    }

}
