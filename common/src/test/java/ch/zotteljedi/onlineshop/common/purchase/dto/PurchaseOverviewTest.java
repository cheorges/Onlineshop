package ch.zotteljedi.onlineshop.common.purchase.dto;

import ch.zotteljedi.onlineshop.common.dto.Id;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class PurchaseOverviewTest {

    private final LocalDate now = LocalDate.now();
    private PurchaseItemOverview purchaseItemOverview;

    @Before
    public void setUp() {
        purchaseItemOverview = ImmutablePurchaseItemOverview.builder()
                .sellerRepresentation("SellerRepresentation")
                .title("Title")
                .unit(1)
                .unitprice(42.0)
                .build();
    }

    @Test
    public void test_create_purchase_overview_dto() {
        // Given
        PurchaseOverview purchaseOverview = ImmutablePurchaseOverview.builder()
                .id(Id.of(1, PurchaseId.class))
                .boughtAt(now)
                .addPurchaseItems(purchaseItemOverview)
                .build();

        // Then
        assertNotNull(purchaseOverview);
        assertThat(purchaseOverview.getId().getValue(), is(1));
        assertThat(purchaseOverview.getBoughtAt(), is(now));
        assertThat(purchaseOverview.getPurchaseItems().size(), is(1));
        assertThat(purchaseOverview.getPurchaseItems().get(0), is(purchaseItemOverview));
    }

    @Test
    public void test_create_purchase_overview_dto_with_multiple_products() {
        // Given
        PurchaseOverview purchaseOverview = ImmutablePurchaseOverview.builder()
                .id(Id.of(1, PurchaseId.class))
                .boughtAt(now)
                .addAllPurchaseItems(Arrays.asList(purchaseItemOverview, purchaseItemOverview))
                .build();

        // Then
        assertNotNull(purchaseOverview);
        assertThat(purchaseOverview.getId().getValue(), is(1));
        assertThat(purchaseOverview.getBoughtAt(), is(now));
        assertThat(purchaseOverview.getPurchaseItems().size(), is(2));
        assertThat(purchaseOverview.getPurchaseItems().get(0), is(purchaseItemOverview));
        assertThat(purchaseOverview.getPurchaseItems().get(1), is(purchaseItemOverview));
    }

}