package ch.zotteljedi.onlineshop.common.purchase.dto;

import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class CartProductTest {

    @Test
    public void test_create_cartproduct_dto() {
        // Given
        CartProduct cartProduct = ImmutableCartProduct.builder()
                .productId(Id.of(1, ProductId.class))
                .unit(2)
                .unitprice(34.5)
                .build();

        // Then
        assertNotNull(cartProduct);
        assertThat(cartProduct.getProductId().getValue(), is(1));
        assertThat(cartProduct.getUnit(), is(2));
        assertThat(cartProduct.getUnitprice(), is(34.5));
    }
}