package ch.zotteljedi.onlineshop.common.dto;

import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IdTest {

    @Test
    public void test_id_are_equals() {
        // Given
        ProductId productId1 = Id.of(1, ProductId.class);
        ProductId productId2 = Id.of(1, ProductId.class);

        // When
        boolean equals = productId1.equals(productId2);

        // Then
        assertTrue(equals);
    }

    @Test
    public void test_id_not_are_equals() {
        // Given
        ProductId productId1 = Id.of(1, ProductId.class);
        ProductId productId2 = Id.of(2, ProductId.class);

        // When
        boolean equals = productId1.equals(productId2);

        // Then
        assertFalse(equals);
    }

}