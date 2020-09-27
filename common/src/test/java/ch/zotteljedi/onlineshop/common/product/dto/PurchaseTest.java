package ch.zotteljedi.onlineshop.common.product.dto;

import ch.zotteljedi.onlineshop.common.customer.dto.Customer;
import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.customer.dto.ImmutableCustomer;
import ch.zotteljedi.onlineshop.common.dto.Id;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class PurchaseTest {

    private CartProduct cartProduct;

    @Before
    public void setUp() {
        cartProduct = cartProduct = ImmutableCartProduct.builder()
                .productId(Id.of(1, ProductId.class))
                .unit(2)
                .unitprice(34.5)
                .build();
    }

    @Test
    public void test_create_purchase_dto() {
        // Given
        ImmutablePurchase purchase = ImmutablePurchase.builder()
                .buyerId(Id.of(1, CustomerId.class))
                .addCartProduct(cartProduct)
                .build();

        // Then
        assertNotNull(purchase);
        assertThat(purchase.getBuyerId().getValue(), is(1));
        assertThat(purchase.getCartProduct().size(), is(1));
        assertThat(purchase.getCartProduct().get(0), is(cartProduct));
    }

    @Test
    public void test_create_purchase_dto_with_multiple_products() {
        // Given
        ImmutablePurchase purchase = ImmutablePurchase.builder()
                .buyerId(Id.of(1, CustomerId.class))
                .addAllCartProduct(Arrays.asList(cartProduct, cartProduct))
                .build();

        // Then
        assertNotNull(purchase);
        assertThat(purchase.getBuyerId().getValue(), is(1));
        assertThat(purchase.getCartProduct().size(), is(2));
        assertThat(purchase.getCartProduct().get(0), is(cartProduct));
        assertThat(purchase.getCartProduct().get(1), is(cartProduct));
    }

}