package ch.zotteljedi.onlineshop.common.purchase.dto;

import ch.zotteljedi.onlineshop.common.dto.Id;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class PurchaseItemOverviewTest {

    @Before
    public void setUp() {

    }

    @Test
    public void test_create_purchase_item_overview_dto() {
        // Given
        PurchaseItemOverview purchaseItemOverview  = ImmutablePurchaseItemOverview.builder()
                .sellerRepresentation("SellerRepresentation")
                .title("Title")
                .unit(1)
                .unitprice(42.0)
                .build();

        // Then
        assertNotNull(purchaseItemOverview);
        assertThat(purchaseItemOverview.getSellerRepresentation(), is("SellerRepresentation"));
        assertThat(purchaseItemOverview.getTitle(), is("Title"));
        assertThat(purchaseItemOverview.getUnit(), is(1));
        assertThat(purchaseItemOverview.getUnitprice(), is(42.0));
    }

}