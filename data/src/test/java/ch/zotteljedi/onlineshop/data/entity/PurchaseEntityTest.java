package ch.zotteljedi.onlineshop.data.entity;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PurchaseEntityTest {

    @Test
    public void test_init_purchase_entity() {
        // Given
        LocalDate now = LocalDate.now();
        CustomerEntity buyer = mock(CustomerEntity.class);

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(1);
        purchaseEntity.setBoughtAt(now);
        purchaseEntity.setBuyer(buyer);

        // Then
        assertThat(purchaseEntity.getId(), is(1));
        assertThat(purchaseEntity.getBoughtAt(), is(now));
        assertThat(purchaseEntity.getBuyer(), is(buyer));
    }
}