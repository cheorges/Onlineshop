package ch.zotteljedi.onlineshop.core.purchase.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.product.dto.ProductId;
import ch.zotteljedi.onlineshop.common.purchase.dto.ImmutableCartProduct;
import ch.zotteljedi.onlineshop.common.purchase.dto.ImmutablePurchase;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerEntityBuilder;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.core.product.service.ProductEntityBuilder;
import ch.zotteljedi.onlineshop.core.product.service.ProductServiceImpl;
import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;
import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import ch.zotteljedi.onlineshop.data.entity.PurchaseEntity;
import ch.zotteljedi.onlineshop.data.entity.PurchaseItemEntity;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ProductPurchaseImplTest {
   private EntityManager em;
   private ProductPurchaseImpl productPurchaseImpl;

   private final CustomerEntity CUSTOMER_ENTITY = new CustomerEntityBuilder()
         .username("username-1")
         .firstname("firstname-1")
         .lastname("lastname-1")
         .email("firstname-1@zotteltec.ch")
         .password("password-1")
         .build();

   private final ProductEntity PRODUCT_ENTITY = new ProductEntityBuilder()
         .title("title-0")
         .description("description-0")
         .stock(5)
         .unitprice(5.0)
         .photo(ProductEntityBuilder.generateRandomByte(5))
         .seller(CUSTOMER_ENTITY)
         .build();


   @Before
   public void initializeDependencies() {
      em = Persistence.createEntityManagerFactory("integration-test").createEntityManager();
      productPurchaseImpl = new ProductPurchaseImpl();
      productPurchaseImpl.em = em;
      productPurchaseImpl.customerService = mock(CustomerServiceImpl.class);
      productPurchaseImpl.productService = mock(ProductServiceImpl.class);

      em.getTransaction().begin();
      em.persist(CUSTOMER_ENTITY);
      em.persist(PRODUCT_ENTITY);
      em.getTransaction().commit();
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
            .when(productPurchaseImpl.customerService)
            .getCustomerEntityById(any());

      doReturn(Optional.of(PRODUCT_ENTITY))
            .when(productPurchaseImpl.productService)
            .getProductEntityById(any());
      doReturn(productPurchaseImpl.getMessageContainer()).when(productPurchaseImpl.productService).removeStockByProductId(any(), any());

      // When
      em.getTransaction().begin();
      MessageContainer messageContainer = productPurchaseImpl.newPurchase(purchase);
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
      assertThat(purchaseItemEntities.get(0).getProduct(), is(PRODUCT_ENTITY));
      assertThat(purchaseItemEntities.get(0).getUnit(), is(2));
      assertThat(purchaseItemEntities.get(0).getUnitprice(), is(5.0));
      assertThat(purchaseItemEntities.get(0).getPurchase(), is(purchaseEntities.get(0)));
   }

}
