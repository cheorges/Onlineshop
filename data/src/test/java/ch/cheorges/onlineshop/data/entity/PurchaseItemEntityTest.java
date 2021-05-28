package ch.cheorges.onlineshop.data.entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PurchaseItemEntityTest {

    @Test
    public void test_init_product_entity() {
        // Given
        ProductEntity product = mock(ProductEntity.class);
        PurchaseEntity purchase = mock(PurchaseEntity.class);

        PurchaseItemEntity purchaseItemEntity = new PurchaseItemEntity();
        purchaseItemEntity.setId(1);
        purchaseItemEntity.setUnit(2);
        purchaseItemEntity.setUnitprice(3.4);
        purchaseItemEntity.setProduct(product);
        purchaseItemEntity.setPurchase(purchase);

        // Then
        assertThat(purchaseItemEntity.getId(), is(1));
        assertThat(purchaseItemEntity.getUnit(), is(2));
        assertThat(purchaseItemEntity.getUnitprice(), is(3.4));
        assertThat(purchaseItemEntity.getProduct(), is(product));
        assertThat(purchaseItemEntity.getPurchase(), is(purchase));
    }

}