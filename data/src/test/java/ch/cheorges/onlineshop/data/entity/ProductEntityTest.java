package ch.cheorges.onlineshop.data.entity;

import org.junit.Test;

import java.security.SecureRandom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ProductEntityTest {

    @Test
    public void test_init_product_entity() {
        // Given
        byte[] photo = generateRandomByte(20);
        CustomerEntity seller = mock(CustomerEntity.class);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1);
        productEntity.setTitle("title");
        productEntity.setDescription("description");
        productEntity.setUnitprice(42.0);
        productEntity.setStock(5);
        productEntity.setPhoto(photo);
        productEntity.setSeller(seller);

        // Then
        assertThat(productEntity.getId(), is(1));
        assertThat(productEntity.getTitle(), is("title"));
        assertThat(productEntity.getDescription(), is("description"));
        assertThat(productEntity.getUnitprice(), is(42.0));
        assertThat(productEntity.getStock(), is(5));
        assertThat(productEntity.getPhoto(), is(photo));
        assertThat(productEntity.getSeller(), is(seller));
    }

    private byte[] generateRandomByte(int size) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }
}