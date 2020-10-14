package ch.zotteljedi.onlineshop.core.sale.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.common.sale.dto.SalesOverview;
import ch.zotteljedi.onlineshop.core.builder.CustomerEntityBuilder;
import ch.zotteljedi.onlineshop.core.builder.ProductEntityBuilder;
import ch.zotteljedi.onlineshop.core.builder.PurchaseEntityBuilder;
import ch.zotteljedi.onlineshop.core.builder.PurchaseItemEntityBuilder;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.core.product.service.ProductServiceImpl;
import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;
import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import ch.zotteljedi.onlineshop.data.entity.PurchaseEntity;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class SalesImplTest {
   public static final LocalDate NOW = LocalDate.now();
   private EntityManager em;
   private SalesImpl salesImpl;

   private final CustomerEntity BUYER = new CustomerEntityBuilder()
         .username("buyer")
         .firstname("buyer-firstname")
         .lastname("buyer-lastname")
         .email("buyer@zotteltec.ch")
         .password("buyer-password")
         .build();

   private final CustomerEntity SELLER = new CustomerEntityBuilder()
         .username("seller")
         .firstname("seller-firstname")
         .lastname("seller-lastname")
         .email("seller-firstname@zotteltec.ch")
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
      salesImpl = new SalesImpl();
      salesImpl.em = em;
      salesImpl.customerService = mock(CustomerServiceImpl.class);
      salesImpl.productService = mock(ProductServiceImpl.class);

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
   public void test_customer_not_found() {
      // Given
      doReturn(Optional.empty()).when(salesImpl.customerService).getCustomerEntityById(any());

      // When
      List<SalesOverview> salesByCustomer = salesImpl.getSalesByCustomer(Id.of(99, CustomerId.class));

      // Then
      assertTrue(salesByCustomer.isEmpty());
   }

   @Test
   public void test_get_sales_by_customer() {
      // Given
      doReturn(Collections.singletonList(PRODUCT_ENTITY_1)).when(salesImpl.productService).getProductEntitiyByCustomerId(any());
      doReturn("buyerRepresentation").when(salesImpl.customerService).buildCustomerRepresentation(any());

      // When
      List<SalesOverview> salesByCustomer = salesImpl.getSalesByCustomer(Id.of(2, CustomerId.class));

      // Then
      assertFalse(salesByCustomer.isEmpty());
      assertThat(salesByCustomer.size(), is(1));
      assertThat(salesByCustomer.get(0).getTitle(), is("title-1"));
      assertNotNull(salesByCustomer.get(0).getSalesItems());
      assertThat(salesByCustomer.get(0).getSalesItems().size(), is(1));
      assertThat(salesByCustomer.get(0).getSalesItems().get(0).getBoughtAt(), is(NOW));
      assertThat(salesByCustomer.get(0).getSalesItems().get(0).getBuyerRepresentation(), is("buyerRepresentation"));
      assertThat(salesByCustomer.get(0).getSalesItems().get(0).getUnit(), is(2));
      assertThat(salesByCustomer.get(0).getSalesItems().get(0).getUnitprice(), is(4.0));
   }

   @Test
   public void test_prduct_not_found() {
      // Given
      doReturn(Optional.empty()).when(salesImpl.productService).getProductEntityById(any());

      // When
      Optional<SalesOverview> salesByCustomer = salesImpl.getSalesByProductId(Id.of(99, ProductId.class));

      // Then
      assertTrue(salesByCustomer.isEmpty());
   }

   @Test
   public void test_sales_overview_by_product_id() {
      // Given
      doReturn(Optional.of(PRODUCT_ENTITY_1)).when(salesImpl.productService).getProductEntityById(any());
      doReturn("buyerRepresentation").when(salesImpl.customerService).buildCustomerRepresentation(any());

      // When
      Optional<SalesOverview> salesByCustomer = salesImpl.getSalesByProductId(Id.of(3, ProductId.class));

      // Then
      assertTrue(salesByCustomer.isPresent());
      assertThat(salesByCustomer.get().getTitle(), is("title-1"));
      assertNotNull(salesByCustomer.get().getSalesItems());
      assertThat(salesByCustomer.get().getSalesItems().size(), is(1));
      assertThat(salesByCustomer.get().getSalesItems().get(0).getBoughtAt(), is(NOW));
      assertThat(salesByCustomer.get().getSalesItems().get(0).getBuyerRepresentation(), is("buyerRepresentation"));
      assertThat(salesByCustomer.get().getSalesItems().get(0).getUnit(), is(2));
      assertThat(salesByCustomer.get().getSalesItems().get(0).getUnitprice(), is(4.0));
   }
}
