package ch.zotteljedi.onlineshop.core.builder;

import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;
import ch.zotteljedi.onlineshop.data.entity.ProductEntity;

import java.security.SecureRandom;

public class ProductEntityBuilder {
    private final ProductEntity productEntity = new ProductEntity();

    public static byte[] generateRandomByte(int size) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }

    public ProductEntityBuilder id(Integer id) {
        productEntity.setId(id);
        return this;
    }

    public ProductEntityBuilder title(String title) {
        productEntity.setTitle(title);
        return this;
    }

    public ProductEntityBuilder description(String description) {
        productEntity.setDescription(description);
        return this;
    }

    public ProductEntityBuilder stock(Integer stock) {
        productEntity.setStock(stock);
        return this;
    }

    public ProductEntityBuilder unitprice(Double unitprice) {
        productEntity.setUnitprice(unitprice);
        return this;
    }

    public ProductEntityBuilder photo(byte[] photo) {
        productEntity.setPhoto(photo);
        return this;
    }

    public ProductEntityBuilder seller(CustomerEntity seller) {
        productEntity.setSeller(seller);
        return this;
    }

    public ProductEntity build() {
        return productEntity;
    }
}
