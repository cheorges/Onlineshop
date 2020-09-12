package ch.zotteljedi.onlineshop.common.product.dto;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.dto.Id;
import org.junit.Test;

import java.security.SecureRandom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NewProductTest {

    @Test
    public void test_create_new_produkt_dto() {
        // Given
        byte[] photo = generateRandomByte(20);
        NewProduct product = ImmutableNewProduct.builder()
                .sellerId(Id.of(1, CustomerId.class))
                .title("title")
                .description("description")
                .price(42.0)
                .photo(photo)
                .build();

        // Then
        assertNotNull(product);
        assertThat(product.getSellerId().getValue(), is(1));
        assertThat(product.getTitle(), is("title"));
        assertThat(product.getDescription(), is("description"));
        assertThat(product.getPrice(), is(42.0));
        assertThat(product.getPhoto(), is(photo));
    }

    @Test
    public void test_create_minimal_new_produkt_dto() {
        // Given
        byte[] photo = generateRandomByte(20);
        NewProduct product = ImmutableNewProduct.builder()
                .sellerId(Id.of(1, CustomerId.class))
                .title("title")
                .price(42.0)
                .photo(photo)
                .build();

        // Then
        assertNotNull(product);
        assertThat(product.getSellerId().getValue(), is(1));
        assertThat(product.getTitle(), is("title"));
        assertNull(product.getDescription());
        assertThat(product.getPrice(), is(42.0));
        assertThat(product.getPhoto(), is(photo));
    }

    private byte[] generateRandomByte(int size) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }
}