package ch.zotteljedi.onlineshop.common.sale.dto;

import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class SalesOverviewTest {

    private final LocalDate now = LocalDate.now();
    private SalesItemOverview salesItemOverview;

    @Before
    public void setUp() {
        salesItemOverview = ImmutableSalesItemOverview.builder()
                .buyerRepresentation("BuyerRepresentation")
                .boughtAt(now)
                .unit(1)
                .unitprice(42.0)
                .build();
    }

    @Test
    public void test_create_sales_overview_dto() {
        // Given
        SalesOverview salesOverview = ImmutableSalesOverview.builder()
                .id(Id.of(1, ProductId.class))
                .title("Title")
                .addSalesItems(salesItemOverview)
                .build();

        // Then
        assertNotNull(salesOverview);
        assertThat(salesOverview.getId().getValue(), is(1));
        assertThat(salesOverview.getTitle(), is("Title"));
        assertThat(salesOverview.getSalesItems().size(), is(1));
        assertThat(salesOverview.getSalesItems().get(0), is(salesItemOverview));
    }

    @Test
    public void test_create_sales_overview_dto_with_multiple_products() {
        // Given
        SalesOverview salesOverview = ImmutableSalesOverview.builder()
                .id(Id.of(1, ProductId.class))
                .title("Title")
                .addAllSalesItems(Arrays.asList(salesItemOverview, salesItemOverview))
                .build();

        // Then
        assertNotNull(salesOverview);
        assertThat(salesOverview.getId().getValue(), is(1));
        assertThat(salesOverview.getTitle(), is("Title"));
        assertThat(salesOverview.getSalesItems().size(), is(2));
        assertThat(salesOverview.getSalesItems().get(0), is(salesItemOverview));
        assertThat(salesOverview.getSalesItems().get(1), is(salesItemOverview));
    }

}