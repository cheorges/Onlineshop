package ch.zotteljedi.onlineshop.web.product.jsf;

import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.message.Message;
import ch.zotteljedi.onlineshop.common.product.dto.ImmutableNewProduct;
import ch.zotteljedi.onlineshop.common.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.common.product.dto.Product;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.common.product.service.ProductServicLocal;
import ch.zotteljedi.onlineshop.web.customer.exception.UnauthorizedAccessException;
import ch.zotteljedi.onlineshop.web.customer.jsf.CustomerJSF;
import ch.zotteljedi.onlineshop.web.customer.jsf.CustomerSessionJSF;
import ch.zotteljedi.onlineshop.web.product.dto.PageProduct;
import ch.zotteljedi.onlineshop.web.product.dto.PersistPageProduct;
import ch.zotteljedi.onlineshop.web.product.mapper.PageProductMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@RequestScoped
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

   public String saveProduct(String title, String description, Double price, Part photo) {
      if (getProduct() instanceof PersistPageProduct) {
         System.out.println("update");
      } else {
         System.out.println("save");
      }

      return "customer_overview";
   }

   public String saleNewProduct(String title, String description, Double price, Part photo) {
      try {
         InputStream inputStream = photo.getInputStream();
         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         byte[] buffer = new byte[(int) photo.getSize()];
         for (int length = 0; (length = inputStream.read(buffer)) > 0; ) {
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
         showMessage(null, () -> "Product created successful.");
         return "customer_overview";

      } catch (IOException | UnauthorizedAccessException e) {
         Logger.getLogger(CustomerJSF.class.getCanonicalName()).log(Level.INFO, e.getMessage());
      }
      return "customer_product";
   }

   public void refreshPageProduct(int id) {
      productServicLocal.getProductById(Id.of(id, ProductId.class)).ifPresent(
            product -> pageProduct = PageProductMapper.INSTANCE.map(product));
   }

   public List<PersistPageProduct> getAll() {
      List<Product> products = productServicLocal.getAllProducts();
      return PageProductMapper.INSTANCE.map(products);
   }

   private void showMessage(final String clientId, final Message message) {
      FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(message.getMessage()));
   }

}
