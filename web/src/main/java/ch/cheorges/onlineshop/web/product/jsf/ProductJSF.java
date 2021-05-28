package ch.cheorges.onlineshop.web.product.jsf;

import ch.cheorges.onlineshop.web.common.exception.ObjectNotFoundByIdException;
import ch.cheorges.onlineshop.web.common.massage.MessageFactory;
import ch.cheorges.onlineshop.web.product.dto.PageProduct;
import ch.cheorges.onlineshop.web.product.dto.PersistPageProduct;
import ch.cheorges.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.product.dto.ImmutableChangeProduct;
import ch.zotteljedi.onlineshop.common.product.dto.ImmutableNewProduct;
import ch.cheorges.onlineshop.common.product.dto.ProductId;
import ch.cheorges.onlineshop.common.product.service.ProductServiceLocal;
import ch.cheorges.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.cheorges.onlineshop.web.customer.jsf.CustomerJSF;
import ch.cheorges.onlineshop.web.customer.jsf.CustomerSessionJSF;
import ch.cheorges.onlineshop.web.product.mapper.PageProductMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@ViewScoped
public class ProductJSF implements Serializable {

    @Inject
    private ProductServiceLocal productServiceLocal;

    @Inject
    private CustomerSessionJSF customerSessionJSF;

    @Inject
    private MessageFactory messageFactory;

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

    public List<PersistPageProduct> getProductsBySeller() throws UnauthorizedAccessException {
        return PageProductMapper.INSTANCE.map(productServiceLocal.getProductsBySeller(customerSessionJSF.getCustomerId()));
    }

    public List<PersistPageProduct> getAllAvailableProducts() {
        return PageProductMapper.INSTANCE.map(productServiceLocal.getAllAvailableProducts());
    }

    public String save(String title, String description, Double price, Integer stock, Part photo) throws UnauthorizedAccessException {
        try {
            if (getProduct() instanceof PersistPageProduct) {
                productServiceLocal.changeProduct(ImmutableChangeProduct.builder()
                        .id(Id.of(getProductId(), ProductId.class))
                        .title(title)
                        .description(description)
                        .unitprice(price)
                        .stock(stock)
                        .photo(createPhotoStream(photo))
                        .sellerId(customerSessionJSF.getCustomerId())
                        .build());
            } else {
                productServiceLocal.addNewProduct(ImmutableNewProduct.builder()
                        .title(title)
                        .description(description)
                        .unitprice(price)
                        .stock(stock)
                        .photo(createPhotoStream(photo))
                        .sellerId(customerSessionJSF.getCustomerId())
                        .build());
            }
            messageFactory.showInfo("Product created successful.");
            return "overview";
        } catch (IOException e) {
            Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
        }

        return "product";
    }

    public void delete(ProductId id) {
        if (!productServiceLocal.deleteProduct(id).hasMessagesThenProvide(msg -> messageFactory.showError(msg))) {
            messageFactory.showInfo("Product deleted successful.");
        }
    }

    private byte[] createPhotoStream(Part photo) throws IOException {
        InputStream inputStream = photo.getInputStream();
        if (photo.getSize() == 0) {
            return productServiceLocal.getProductById(Id.of(getProductId(), ProductId.class)).orElseThrow(ObjectNotFoundByIdException::new).getPhoto();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[(int) photo.getSize()];
        for (int length = 0; (length = inputStream.read(buffer)) > 0; ) {
            outputStream.write(buffer, 0, length);
        }
        return outputStream.toByteArray();
    }

    public void refreshPageProduct(int id) {
        productServiceLocal.getProductById(Id.of(id, ProductId.class)).ifPresent(
                product -> pageProduct = PageProductMapper.INSTANCE.map(product));
    }

}
