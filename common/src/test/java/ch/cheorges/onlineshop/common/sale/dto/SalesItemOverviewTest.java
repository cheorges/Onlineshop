package ch.cheorges.onlineshop.common.sale.dto;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class SalesItemOverviewTest {

    private static LocalDate now = LocalDate.now();

    @Test
    public void test_create_sales_item_overview_dto() {
        // Given
        SalesItemOverview purchaseItemOverview = ImmutableSalesItemOverview.builder()
                .buyerRepresentation("BuyerRepresentation")
                .boughtAt(now)
                .unit(1)
                .unitprice(42.0)
                .build();

        // Then
        assertNotNull(purchaseItemOverview);
        assertThat(purchaseItemOverview.getBuyerRepresentation(), is("BuyerRepresentation"));
        assertThat(purchaseItemOverview.getBoughtAt(), is(now));
        assertThat(purchaseItemOverview.getUnit(), is(1));
        assertThat(purchaseItemOverview.getUnitprice(), is(42.0));
    }

}