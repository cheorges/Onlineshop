package ch.cheorges.onlineshop.common.product.dto;

import ch.cheorges.onlineshop.common.customer.dto.Customer;
import ch.cheorges.onlineshop.common.customer.dto.CustomerId;
import ch.cheorges.onlineshop.common.dto.Id;
import ch.cheorges.onlineshop.common.customer.dto.ImmutableCustomer;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ProductTest {

    private Customer customer;

    @Before
    public void setUp() {
        customer = ImmutableCustomer.builder()
                .id(Id.of(1, CustomerId.class))
                .username("username")
                .firstname("firstname")
                .lastname("lastname")
                .email("email")
                .password("password")
                .build();
    }

    @Test
    public void test_create_produkt_dto() {
        // Given
        byte[] photo = generateRandomByte(20);
        Product product = ImmutableProduct.builder()
                .id(Id.of(1, ProductId.class))
                .seller(customer)
                .title("title")
                .description("description")
                .unitprice(42.0)
                .stock(5)
                .photo(photo)
                .build();

        // Then
        assertNotNull(product);
        MatcherAssert.assertThat(product.getId().getValue(), is(1));
        assertThat(product.getSeller(), is(customer));
        assertThat(product.getTitle(), is("title"));
        assertThat(product.getDescription(), is("description"));
        assertThat(product.getUnitprice(), is(42.0));
        assertThat(product.getStock(), is(5));
        assertThat(product.getPhoto(), is(photo));
    }

    @Test
    public void test_create_minimal_produkt_dto() {
        // Given
        byte[] photo = generateRandomByte(20);
        Product product = ImmutableProduct.builder()
                .id(Id.of(1, ProductId.class))
                .seller(customer)
                .title("title")
                .unitprice(42.0)
                .stock(5)
                .photo(photo)
                .build();

        // Then
        assertNotNull(product);
        MatcherAssert.assertThat(product.getId().getValue(), is(1));
        assertThat(product.getSeller(), is(customer));
        assertThat(product.getTitle(), is("title"));
        assertNull(product.getDescription());
        assertThat(product.getUnitprice(), is(42.0));
        assertThat(product.getStock(), is(5));
        assertThat(product.getPhoto(), is(photo));
    }

    private byte[] generateRandomByte(int size) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }
}