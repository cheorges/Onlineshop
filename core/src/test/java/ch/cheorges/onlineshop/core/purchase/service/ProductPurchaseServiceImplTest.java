package ch.cheorges.onlineshop.core.purchase.service;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import ch.cheorges.onlineshop.common.customer.dto.CustomerId;
import ch.cheorges.onlineshop.common.dto.Id;
import ch.cheorges.onlineshop.common.message.MessageContainer;
import ch.cheorges.onlineshop.common.product.dto.ProductId;
import ch.cheorges.onlineshop.common.purchase.dto.ImmutableCartProduct;
import ch.cheorges.onlineshop.common.purchase.dto.ImmutablePurchase;
import ch.cheorges.onlineshop.common.purchase.dto.ImmutableCartProduct;
import ch.cheorges.onlineshop.common.purchase.dto.ImmutablePurchase;
import ch.cheorges.onlineshop.core.builder.CustomerEntityBuilder;
import ch.cheorges.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.cheorges.onlineshop.core.builder.ProductEntityBuilder;
import ch.cheorges.onlineshop.core.product.service.ProductServiceImpl;
import ch.cheorges.onlineshop.data.entity.CustomerEntity;
import ch.cheorges.onlineshop.data.entity.ProductEntity;
import ch.cheorges.onlineshop.data.entity.PurchaseEntity;
import ch.cheorges.onlineshop.data.entity.PurchaseItemEntity;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ProductPurchaseServiceImplTest {
    private EntityManager em;
    private ProductPurchaseServiceServiceImpl productPurchaseServiceImpl;

    private final CustomerEntity CUSTOMER_ENTITY = new CustomerEntityBuilder()
            .username("username-1")
            .firstname("firstname-1")
            .lastname("lastname-1")
            .email("firstname-1@zotteltec.ch")
            .password("password-1")
            .build();

    private final ProductEntity PRODUCT_ENTITY_1 = new ProductEntityBuilder()
            .title("title-1")
            .description("description-1")
            .stock(5)
            .unitprice(5.0)
            .photo(ProductEntityBuilder.generateRandomByte(5))
            .seller(CUSTOMER_ENTITY)
            .build();

    private final ProductEntity PRODUCT_ENTITY_2 = new ProductEntityBuilder()
            .title("title-2")
            .description("description-2")
            .stock(5)
            .unitprice(5.0)
            .photo(ProductEntityBuilder.generateRandomByte(5))
            .seller(CUSTOMER_ENTITY)
            .build();

    @Before
    public void initializeDependencies() {
        em = Persistence.createEntityManagerFactory("integration-test").createEntityManager();
        productPurchaseServiceImpl = new ProductPurchaseServiceServiceImpl();
        productPurchaseServiceImpl.em = em;
        productPurchaseServiceImpl.customerService = mock(CustomerServiceImpl.class);
        productPurchaseServiceImpl.productService = mock(ProductServiceImpl.class);

        em.getTransaction().begin();
        em.persist(CUSTOMER_ENTITY);
        em.persist(PRODUCT_ENTITY_1);
        em.persist(PRODUCT_ENTITY_2);
        em.getTransaction().commit();
    }

    @Test
    public void test_no_items() {
        // Given
        ImmutablePurchase purchase = ImmutablePurchase.builder()
                .buyerId(Id.of(1, CustomerId.class)).build();

        // When
        MessageContainer messageContainer = productPurchaseServiceImpl.newPurchase(purchase);

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(1));
        assertThat(messageContainer.getMessages().get(0).getMessage(), is("No products in the shoppingcart."));
    }

    @Test
    public void test_customer_not_found() {
        // Given
        ImmutablePurchase purchase = ImmutablePurchase.builder()
                .buyerId(Id.of(99, CustomerId.class))
                .addCartProduct(ImmutableCartProduct.builder()
                        .productId(Id.of(2, ProductId.class))
                        .unit(2)
                        .unitprice(5.0)
                        .build())
                .build();

        doReturn(Optional.empty())
                .when(productPurchaseServiceImpl.customerService)
                .getCustomerEntityById(any());

        // When
        MessageContainer messageContainer = productPurchaseServiceImpl.newPurchase(purchase);

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(2));
        MatcherAssert.assertThat(messageContainer.getMessages().get(0).getMessage(), anyOf(is("Customer by ID '99' not found."), is("Buyer may not be blank.")));
        MatcherAssert.assertThat(messageContainer.getMessages().get(1).getMessage(), anyOf(is("Customer by ID '99' not found."), is("Buyer may not be blank.")));
    }

    @Test
    public void test_product_not_found() {
        // Given
        ImmutablePurchase purchase = ImmutablePurchase.builder()
                .buyerId(Id.of(1, CustomerId.class))
                .addCartProduct(ImmutableCartProduct.builder()
                        .productId(Id.of(99, ProductId.class))
                        .unit(2)
                        .unitprice(5.0)
                        .build())
                .build();

        doReturn(Optional.of(CUSTOMER_ENTITY))
                .when(productPurchaseServiceImpl.customerService)
                .getCustomerEntityById(any());

        doReturn(Optional.empty())
                .when(productPurchaseServiceImpl.productService)
                .getProductEntityById(any());

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productPurchaseServiceImpl.newPurchase(purchase);
        em.getTransaction().commit();

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(3));
        MatcherAssert.assertThat(messageContainer.getMessages().get(0).getMessage(), anyOf(is("Product by ID '99' not found."), is("Product may not be blank.")));
        MatcherAssert.assertThat(messageContainer.getMessages().get(1).getMessage(), anyOf(is("Product by ID '99' not found."), is("Product may not be blank.")));
        MatcherAssert.assertThat(messageContainer.getMessages().get(2).getMessage(), anyOf(is("Product by ID '99' not found."), is("Product may not be blank.")));

        List<PurchaseEntity> purchaseEntities = em.createNamedQuery("PurchaseEntitiy.get", PurchaseEntity.class).getResultList();
        assertTrue(purchaseEntities.isEmpty());
    }

    @Test
    public void test_buy_two_unit_from_the_same_product() {
        // Given
        ImmutablePurchase purchase = ImmutablePurchase.builder()
                .buyerId(Id.of(1, CustomerId.class))
                .addCartProduct(ImmutableCartProduct.builder()
                        .productId(Id.of(2, ProductId.class))
                        .unit(2)
                        .unitprice(5.0)
                        .build())
                .build();

        doReturn(Optional.of(CUSTOMER_ENTITY))
                .when(productPurchaseServiceImpl.customerService)
                .getCustomerEntityById(any());

        doReturn(Optional.of(PRODUCT_ENTITY_1))
                .when(productPurchaseServiceImpl.productService)
                .getProductEntityById(any());
        doReturn(productPurchaseServiceImpl.getMessageContainer()).when(productPurchaseServiceImpl.productService).removeStockByProductId(any(), any());

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productPurchaseServiceImpl.newPurchase(purchase);
        em.getTransaction().commit();

        // Then
        assertFalse(messageContainer.hasMessages());

        List<PurchaseEntity> purchaseEntities = em.createNamedQuery("PurchaseEntitiy.get", PurchaseEntity.class).getResultList();
        assertFalse(purchaseEntities.isEmpty());
        assertThat(purchaseEntities.size(), is(1));
        assertThat(purchaseEntities.get(0).getBoughtAt(), is(LocalDate.now()));
        assertThat(purchaseEntities.get(0).getBuyer(), is(CUSTOMER_ENTITY));

        List<PurchaseItemEntity> purchaseItemEntities = em.createNamedQuery("PurchaseItemEntity.get", PurchaseItemEntity.class).getResultList();
        assertFalse(purchaseItemEntities.isEmpty());
        assertThat(purchaseItemEntities.size(), is(1));
        assertThat(purchaseItemEntities.get(0).getProduct(), is(PRODUCT_ENTITY_1));
        assertThat(purchaseItemEntities.get(0).getUnit(), is(2));
        assertThat(purchaseItemEntities.get(0).getUnitprice(), is(5.0));
        assertThat(purchaseItemEntities.get(0).getPurchase(), is(purchaseEntities.get(0)));
    }

    @Test
    public void test_buy_two_unit_from_the_two_product() {
        // Given
        ImmutablePurchase purchase = ImmutablePurchase.builder()
                .buyerId(Id.of(1, CustomerId.class))
                .addCartProduct(ImmutableCartProduct.builder()
                        .productId(Id.of(2, ProductId.class))
                        .unit(2)
                        .unitprice(5.0)
                        .build())
                .addCartProduct(ImmutableCartProduct.builder()
                        .productId(Id.of(3, ProductId.class))
                        .unit(2)
                        .unitprice(5.0)
                        .build())
                .build();

        doReturn(Optional.of(CUSTOMER_ENTITY))
                .when(productPurchaseServiceImpl.customerService)
                .getCustomerEntityById(any());

        doReturn(Optional.of(PRODUCT_ENTITY_1))
                .when(productPurchaseServiceImpl.productService)
                .getProductEntityById(Id.of(2, ProductId.class));
        doReturn(Optional.of(PRODUCT_ENTITY_2))
                .when(productPurchaseServiceImpl.productService)
                .getProductEntityById(Id.of(3, ProductId.class));
        doReturn(productPurchaseServiceImpl.getMessageContainer()).when(productPurchaseServiceImpl.productService).removeStockByProductId(any(), any());

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productPurchaseServiceImpl.newPurchase(purchase);
        em.getTransaction().commit();

        // Then
        assertFalse(messageContainer.hasMessages());

        List<PurchaseEntity> purchaseEntities = em.createNamedQuery("PurchaseEntitiy.get", PurchaseEntity.class).getResultList();
        assertFalse(purchaseEntities.isEmpty());
        assertThat(purchaseEntities.size(), is(1));
        assertThat(purchaseEntities.get(0).getBoughtAt(), is(LocalDate.now()));
        assertThat(purchaseEntities.get(0).getBuyer(), is(CUSTOMER_ENTITY));

        List<PurchaseItemEntity> purchaseItemEntities = em.createNamedQuery("PurchaseItemEntity.get", PurchaseItemEntity.class).getResultList();
        assertFalse(purchaseItemEntities.isEmpty());
        assertThat(purchaseItemEntities.size(), is(2));
    }
}
