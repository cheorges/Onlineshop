package ch.cheorges.onlineshop.common.product.dto;

import ch.cheorges.onlineshop.common.dto.Id;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class ProductIdTest {

    @Test
    public void test_create_product_id() {
        // Given
        ProductId id = Id.of(1, ProductId.class);

        // Then
        assertNotNull(id);
        MatcherAssert.assertThat(id.getValue(), is(1));
    }

}