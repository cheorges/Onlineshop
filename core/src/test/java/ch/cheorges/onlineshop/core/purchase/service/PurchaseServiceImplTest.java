package ch.cheorges.onlineshop.core.purchase.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import ch.cheorges.onlineshop.common.customer.dto.CustomerId;
import ch.cheorges.onlineshop.common.dto.Id;
import ch.cheorges.onlineshop.common.purchase.dto.PurchaseId;
import ch.cheorges.onlineshop.common.purchase.dto.PurchaseOverview;
import ch.cheorges.onlineshop.core.builder.CustomerEntityBuilder;
import ch.cheorges.onlineshop.core.builder.PurchaseEntityBuilder;
import ch.cheorges.onlineshop.core.builder.PurchaseItemEntityBuilder;
import ch.cheorges.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.cheorges.onlineshop.core.builder.ProductEntityBuilder;
import ch.cheorges.onlineshop.data.entity.CustomerEntity;
import ch.cheorges.onlineshop.data.entity.ProductEntity;
import ch.cheorges.onlineshop.data.entity.PurchaseEntity;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class PurchaseServiceImplTest {
    public static final LocalDate NOW = LocalDate.now();
    private EntityManager em;
    private PurchaseServiceImpl purchaseServiceImpl;

    private final CustomerEntity BUYER = new CustomerEntityBuilder()
            .username("buyer")
            .firstname("buyer-firstname")
            .lastname("buyer-lastname")
            .email("buyer@cheorgestec.ch")
            .password("buyer-password")
            .build();

    private final CustomerEntity SELLER = new CustomerEntityBuilder()
            .username("seller")
            .firstname("seller-firstname")
            .lastname("seller-lastname")
            .email("seller-firstname@cheorgestec.ch")
            .password("seller-password")
            .build();

    private final ProductEntity PRODUCT_ENTITY_1 = new ProductEntityBuilder()
            .title("title-1")
            .description("description-1")
            .stock(5)
            .unitprice(5.0)
            .photo(ProductEntityBuilder.generateRandomByte(5))
            .seller(SELLER)
            .build();

    @Before
    public void initializeDependencies() {
        em = Persistence.createEntityManagerFactory("integration-test").createEntityManager();
        purchaseServiceImpl = new PurchaseServiceImpl();
        purchaseServiceImpl.em = em;
        purchaseServiceImpl.customerService = mock(CustomerServiceImpl.class);

        em.getTransaction().begin();
        em.persist(BUYER);
        em.persist(SELLER);
        em.persist(PRODUCT_ENTITY_1);
        PurchaseEntity purchaseEntity = new PurchaseEntityBuilder()
                .boughtAt(NOW)
                .buyer(BUYER)
                .build();
        em.persist(purchaseEntity);
        em.persist(new PurchaseItemEntityBuilder()
                .product(PRODUCT_ENTITY_1)
                .unit(2)
                .unitprice(4.0)
                .purchase(purchaseEntity)
                .build());
        em.getTransaction().commit();
    }

    @Test
    public void test_no_customer_found_by_id() {
        // Given
        doReturn(Optional.empty()).when(purchaseServiceImpl.customerService).getCustomerEntityById(any());

        // When
        List<PurchaseOverview> purchaseByCustomer = purchaseServiceImpl.getPurchaseByCustomer(Id.of(99, CustomerId.class));

        // Then
        assertTrue(purchaseByCustomer.isEmpty());
    }

    @Test
    public void test_get_all_purchase_by_customer() {
        // Given
        doReturn(Optional.of(BUYER)).when(purchaseServiceImpl.customerService).getCustomerEntityById(any());
        doReturn("sellerRepresentation").when(purchaseServiceImpl.customerService).buildCustomerRepresentation(any());

        // When
        List<PurchaseOverview> purchaseByCustomer = purchaseServiceImpl.getPurchaseByCustomer(Id.of(1, CustomerId.class));

        // Then
        assertFalse(purchaseByCustomer.isEmpty());
        assertThat(purchaseByCustomer.size(), is(1));
        assertThat(purchaseByCustomer.get(0).getBoughtAt(), is(NOW));
        assertNotNull(purchaseByCustomer.get(0).getPurchaseItems());
        assertThat(purchaseByCustomer.get(0).getPurchaseItems().size(), is(1));
        assertThat(purchaseByCustomer.get(0).getPurchaseItems().get(0).getSellerRepresentation(), is("sellerRepresentation"));
        assertThat(purchaseByCustomer.get(0).getPurchaseItems().get(0).getTitle(), is("title-1"));
        assertThat(purchaseByCustomer.get(0).getPurchaseItems().get(0).getUnit(), is(2));
        assertThat(purchaseByCustomer.get(0).getPurchaseItems().get(0).getUnitprice(), is(4.0));
    }

    @Test
    public void test_purchase_by_id_not_found() {
        // When
        Optional<PurchaseOverview> purchase = purchaseServiceImpl.getPurchaseById(Id.of(99, PurchaseId.class));

        // Then
        assertTrue(purchase.isEmpty());
    }

    @Test
    public void test_get_purchase_by_product_id() {
        // Given
        doReturn("sellerRepresentation").when(purchaseServiceImpl.customerService).buildCustomerRepresentation(any());

        // When
        Optional<PurchaseOverview> purchase = purchaseServiceImpl.getPurchaseById(Id.of(4, PurchaseId.class));

        // Then
        assertTrue(purchase.isPresent());
        assertThat(purchase.get().getBoughtAt(), is(NOW));
        assertNotNull(purchase.get().getPurchaseItems());
        assertThat(purchase.get().getPurchaseItems().size(), is(1));
        assertThat(purchase.get().getPurchaseItems().get(0).getSellerRepresentation(), is("sellerRepresentation"));
        assertThat(purchase.get().getPurchaseItems().get(0).getTitle(), is("title-1"));
        assertThat(purchase.get().getPurchaseItems().get(0).getUnit(), is(2));
        assertThat(purchase.get().getPurchaseItems().get(0).getUnitprice(), is(4.0));
    }

}
