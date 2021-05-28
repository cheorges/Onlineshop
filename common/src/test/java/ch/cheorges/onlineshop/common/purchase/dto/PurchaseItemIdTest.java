package ch.cheorges.onlineshop.common.purchase.dto;

import ch.cheorges.onlineshop.common.dto.Id;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class PurchaseItemIdTest {

    @Test
    public void test_create_purchase_item_id() {
        // Given
        PurchaseItemId id = Id.of(1, PurchaseItemId.class);

        // Then
        assertNotNull(id);
        assertThat(id.getValue(), is(1));
    }

}