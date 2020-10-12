package ch.zotteljedi.onlineshop.core.product.service;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.product.dto.ImmutableNewProduct;
import ch.zotteljedi.onlineshop.common.product.dto.NewProduct;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerEntityBuilder;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;
import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ProductServiceImplTest {
    private EntityManager em;
    private ProductServiceImpl productServiceImpl;

    private final CustomerEntity CUSTOMER_ENTITY = new CustomerEntityBuilder()
                .username("username-1")
                .firstname("firstname-1")
                .lastname("lastname-1")
                .email("firstname-1@zotteltec.ch")
                .password("password-1")
                .build();

    @Before
    public void initializeDependencies() {
        em = Persistence.createEntityManagerFactory("integration-test").createEntityManager();
        productServiceImpl = new ProductServiceImpl();
        productServiceImpl.em = em;
        productServiceImpl.customerService = mock(CustomerServiceImpl.class);
        setUp();
    }

    private void setUp() {
        em.getTransaction().begin();
        em.persist(CUSTOMER_ENTITY);
        em.persist(new ProductEntityBuilder()
                .title("title-1")
                .description("description-1")
                .stock(1)
                .unitprice(1.1)
                .photo(ProductEntityBuilder.generateRandomByte(1))
                .seller(CUSTOMER_ENTITY)
                .build());
        em.getTransaction().commit();
    }

    @Test
    public void test_create_new_product() {
        // Given
        byte[] photo = ProductEntityBuilder.generateRandomByte(20);
        NewProduct newProduct = ImmutableNewProduct.builder()
                .title("title-2")
                .description("discription-2")
                .stock(2)
                .unitprice(2.2)
                .photo(photo)
                .sellerId(Id.of(1, CustomerId.class))
                .build();

        doReturn(Optional.of(CUSTOMER_ENTITY))
                .when(productServiceImpl.customerService)
                .getCustomerEntityById(any());

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productServiceImpl.addNewProduct(newProduct);
        em.getTransaction().commit();

        // Then
        assertFalse(messageContainer.hasMessages());

        List<ProductEntity> resultList = em.createNamedQuery("ProductEntity.get", ProductEntity.class).getResultList();
        assertNotNull(resultList);
        assertThat(resultList.size(), is(2));
    }

    @Test
    public void test_create_new_product_failed() {
        // Given
        byte[] photo = ProductEntityBuilder.generateRandomByte(20);
        NewProduct newProduct = ImmutableNewProduct.builder()
                .title("title-2")
                .description("discription-2")
                .stock(2)
                .unitprice(2.2)
                .photo(photo)
                .sellerId(Id.of(1, CustomerId.class))
                .build();

        doReturn(Optional.empty())
                .when(productServiceImpl.customerService)
                .getCustomerEntityById(any());

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productServiceImpl.addNewProduct(newProduct);
        em.getTransaction().commit();

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(1));
        assertThat(messageContainer.getMessages().get(0).getMessage(), is("Customer by ID '1' not found."));
    }

}