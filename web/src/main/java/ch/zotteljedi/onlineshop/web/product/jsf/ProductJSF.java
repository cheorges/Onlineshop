package ch.zotteljedi.onlineshop.web.product.jsf;

import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.message.Message;
import ch.zotteljedi.onlineshop.common.product.dto.*;
import ch.zotteljedi.onlineshop.common.product.service.ProductServicLocal;
import ch.zotteljedi.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.web.customer.jsf.CustomerJSF;
import ch.zotteljedi.onlineshop.web.customer.jsf.CustomerSessionJSF;
import ch.zotteljedi.onlineshop.web.product.dto.PageProduct;
import ch.zotteljedi.onlineshop.web.product.dto.PersistPageProduct;
import ch.zotteljedi.onlineshop.web.product.mapper.PageProductMapper;
import org.immutables.value.Value;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@ViewScoped
public class ProductJSF implements Serializable {

    @Inject
    private ProductServicLocal productServicLocal;

    @Inject
    private CustomerSessionJSF customerSessionJSF;

    private PageProduct pageProduct = new PageProduct();
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public PageProduct getProduct() {
        return pageProduct;
    }

    public List<Product> getProductsBySeller() throws UnauthorizedAccessException {
        return productServicLocal.getProductsBySeller(customerSessionJSF.getCustomerId());
    }

    public String save(String title, String description, Double price, Part photo) throws UnauthorizedAccessException {
        try {
            if (getProduct() instanceof PersistPageProduct) {
                productServicLocal.changeProduct(ImmutableChangeProduct.builder()
                        .id(Id.of(getProductId(), ProductId.class))
                        .title(title)
                        .description(description)
                        .price(price)
                        .photo(createPhotoStream(photo))
                        .sellerId(customerSessionJSF.getCustomerId())
                        .build());
            } else {
                productServicLocal.addNewProduct(ImmutableNewProduct.builder()
                        .title(title)
                        .description(description)
                        .price(price)
                        .photo(createPhotoStream(photo))
                        .customerId(customerSessionJSF.getCustomerId())
                        .build());
            }
            showMessage(() -> "Product created successful.");
            return "overview";
        } catch (IOException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }

        return "product";
    }

    private byte[] createPhotoStream(Part photo) throws IOException {
        InputStream inputStream = photo.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[(int) photo.getSize()];
        for (int length = 0; (length = inputStream.read(buffer)) > 0; ) {
            outputStream.write(buffer, 0, length);
        }
        return outputStream.toByteArray();
    }

    public void refreshPageProduct(int id) {
        productServicLocal.getProductById(Id.of(id, ProductId.class)).ifPresent(
                product -> pageProduct = PageProductMapper.INSTANCE.map(product));
    }

    public List<PersistPageProduct> getAll() {
        List<Product> products = productServicLocal.getAllProducts();
        return PageProductMapper.INSTANCE.map(products);
    }

    private void showMessage(final Message message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message.getMessage()));
    }

}
