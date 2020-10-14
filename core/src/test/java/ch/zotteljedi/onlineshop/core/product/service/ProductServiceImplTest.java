package ch.zotteljedi.onlineshop.core.product.service;

import ch.zotteljedi.onlineshop.common.customer.dto.CustomerId;
import ch.zotteljedi.onlineshop.common.dto.Id;
import ch.zotteljedi.onlineshop.common.message.MessageContainer;
import ch.zotteljedi.onlineshop.common.product.dto.*;
import ch.zotteljedi.onlineshop.core.builder.CustomerEntityBuilder;
import ch.zotteljedi.onlineshop.core.builder.ProductEntityBuilder;
import ch.zotteljedi.onlineshop.core.customer.service.CustomerServiceImpl;
import ch.zotteljedi.onlineshop.data.entity.CustomerEntity;
import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ProductServiceImplTest {
    private EntityManager em;
    private ProductServiceImpl productServiceImpl;

    private final CustomerEntity CUSTOMER_ENTITY_1 = new CustomerEntityBuilder()
                .username("username-1")
                .firstname("firstname-1")
                .lastname("lastname-1")
                .email("firstname-1@zotteltec.ch")
                .password("password-1")
                .build();

    private final CustomerEntity CUSTOMER_ENTITY_2 = new CustomerEntityBuilder()
            .username("username-2")
            .firstname("firstname-2")
            .lastname("lastname-2")
            .email("firstname-2@zotteltec.ch")
            .password("password-2")
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
        em.persist(CUSTOMER_ENTITY_1);
        em.persist(CUSTOMER_ENTITY_2);
        em.persist(new ProductEntityBuilder()
                .title("title-0")
                .description("description-0")
                .stock(0)
                .unitprice(0.0)
                .photo(ProductEntityBuilder.generateRandomByte(0))
                .seller(CUSTOMER_ENTITY_2)
                .build());
        em.persist(new ProductEntityBuilder()
                .title("title-1")
                .description("description-1")
                .stock(1)
                .unitprice(1.1)
                .photo(ProductEntityBuilder.generateRandomByte(1))
                .seller(CUSTOMER_ENTITY_1)
                .build());
        em.persist(new ProductEntityBuilder()
                .title("title-2")
                .description("description-2")
                .stock(2)
                .unitprice(2.2)
                .photo(ProductEntityBuilder.generateRandomByte(2))
                .seller(CUSTOMER_ENTITY_1)
                .build());
        em.getTransaction().commit();
    }

    @Test
    public void test_create_new_product() {
        // Given
        byte[] photo = ProductEntityBuilder.generateRandomByte(3);
        NewProduct newProduct = ImmutableNewProduct.builder()
                .title("title-3")
                .description("discription-3")
                .stock(3)
                .unitprice(3.3)
                .photo(photo)
                .sellerId(Id.of(1, CustomerId.class))
                .build();

        doReturn(Optional.of(CUSTOMER_ENTITY_1))
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
        assertThat(resultList.size(), is(4));
    }

    @Test
    public void test_create_new_product_failed() {
        // Given
        byte[] photo = ProductEntityBuilder.generateRandomByte(4);
        NewProduct newProduct = ImmutableNewProduct.builder()
                .title("title-4")
                .description("discription-4")
                .stock(4)
                .unitprice(4.4)
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
        assertThat(messageContainer.getMessages().size(), is(2));
        assertThat(messageContainer.getMessages().get(0).getMessage(), is("Customer by ID '1' not found."));
        assertThat(messageContainer.getMessages().get(1).getMessage(), is("Seller may not be blank."));
    }

    @Test
    public void test_change_product() {
        // Given
        byte[] photo = ProductEntityBuilder.generateRandomByte(22);
        ChangeProduct changeProduct = ImmutableChangeProduct.builder()
                .id(Id.of(5, ProductId.class))
                .title("new-title-2")
                .description("new-discription-2")
                .stock(22)
                .unitprice(22.20)
                .photo(photo)
                .sellerId(Id.of(1, CustomerId.class))
                .build();

        doReturn(Optional.of(CUSTOMER_ENTITY_1))
                .when(productServiceImpl.customerService)
                .getCustomerEntityById(any());

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productServiceImpl.changeProduct(changeProduct);
        em.getTransaction().commit();

        // Then
        assertFalse(messageContainer.hasMessages());

        Optional<ProductEntity> changedProduct = em.createNamedQuery("ProductEntity.getById", ProductEntity.class)
                .setParameter("id", 5)
                .getResultList().stream().findFirst();
        assertTrue(changedProduct.isPresent());
        assertThat(changedProduct.get().getTitle(), is("new-title-2"));
        assertThat(changedProduct.get().getDescription(), is("new-discription-2"));
        assertThat(changedProduct.get().getStock(), is(22));
        assertThat(changedProduct.get().getUnitprice(), is(22.20));
        assertThat(changedProduct.get().getPhoto(), is(photo));
        assertThat(changedProduct.get().getSeller(), is(CUSTOMER_ENTITY_1));
    }

    @Test
    public void test_change_product_failed() {
        // Given
        byte[] photo = ProductEntityBuilder.generateRandomByte(22);
        ChangeProduct changeProduct = ImmutableChangeProduct.builder()
                .id(Id.of(2, ProductId.class))
                .title("")
                .description("new-discription-2")
                .stock(-1)
                .unitprice(22.22)
                .photo(photo)
                .sellerId(Id.of(1, CustomerId.class))
                .build();

        doReturn(Optional.of(CUSTOMER_ENTITY_1))
                .when(productServiceImpl.customerService)
                .getCustomerEntityById(any());

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productServiceImpl.changeProduct(changeProduct);
        em.getTransaction().commit();

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(2));
        assertThat(messageContainer.getMessages().get(0).getMessage(), anyOf(is("Title may not be empty."), is("Title must be between 1 and 255 characters.")));
        assertThat(messageContainer.getMessages().get(1).getMessage(), anyOf(is("Title may not be empty."), is("Title must be between 1 and 255 characters.")));
    }

    @Test
    public void test_get_all_available_products() {
        // When
        List<Product> allAvailableProducts = productServiceImpl.getAllAvailableProducts();

        // Then
        assertThat(allAvailableProducts.size(), is(2));
    }

    @Test
    public void test_get_product_by_id() {
        // When
        Optional<Product> product = productServiceImpl.getProductById(Id.of(4, ProductId.class));

        // Then
        assertTrue(product.isPresent());
    }

    @Test
    public void test_product_by_id_not_found() {
        // When
        Optional<Product> product = productServiceImpl.getProductById(Id.of(99, ProductId.class));

        // Then
        assertTrue(product.isEmpty());
    }

    @Test
    public void test_get_product_by_seller() {
        // Given
        doReturn(Optional.of(CUSTOMER_ENTITY_1))
                .when(productServiceImpl.customerService)
                .getCustomerEntityById(any());

        // When
        List<Product> productsBySeller = productServiceImpl.getProductsBySeller(mock(CustomerId.class));

        // Then
        assertFalse(productsBySeller.isEmpty());
        assertThat(productsBySeller.size(), is(2));
    }

    @Test
    public void test_seller_has_no_products() {
        // Given
        doReturn(Optional.empty())
                .when(productServiceImpl.customerService)
                .getCustomerEntityById(any());

        // When
        List<Product> productsBySeller = productServiceImpl.getProductsBySeller(mock(CustomerId.class));

        // Then
        assertTrue(productsBySeller.isEmpty());
    }

    @Test
    public void test_delete_product_failed() {
        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productServiceImpl.deleteProduct(Id.of(99, ProductId.class));
        em.getTransaction().commit();

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(1));
        assertThat(messageContainer.getMessages().get(0).getMessage(), is("Product by ID '99' not found."));
    }

    @Test
    public void test_delete_product() {
        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productServiceImpl.deleteProduct(Id.of(5, ProductId.class));
        em.getTransaction().commit();

        // Then
        assertFalse(messageContainer.hasMessages());
        List<ProductEntity> allProducts = em.createNamedQuery("ProductEntity.get", ProductEntity.class)
                .getResultList();
        assertThat(allProducts.size(), is(2));
    }

    @Test
    public void test_remove_stock_from_product() {
        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productServiceImpl.removeStockByProductId(Id.of(5, ProductId.class), 1);
        em.getTransaction().commit();

        // Then
        assertFalse(messageContainer.hasMessages());

        Optional<ProductEntity> changedProduct = em.createNamedQuery("ProductEntity.getById", ProductEntity.class)
                .setParameter("id", 5)
                .getResultList().stream().findFirst();
        assertTrue(changedProduct.isPresent());
        assertThat(changedProduct.get().getStock(), is(1));
    }

    @Test
    public void test_stock_has_not_enougth_product() {
        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productServiceImpl.removeStockByProductId(Id.of(5, ProductId.class), 3);
        em.getTransaction().commit();

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(1));
        assertThat(messageContainer.getMessages().get(0).getMessage(), is("Product stock '2', there are not enouth products aviable."));

        Optional<ProductEntity> changedProduct = em.createNamedQuery("ProductEntity.getById", ProductEntity.class)
                .setParameter("id", 5)
                .getResultList().stream().findFirst();
        assertTrue(changedProduct.isPresent());
        assertThat(changedProduct.get().getStock(), is(2));
    }

    @Test
    public void test_product_not_found_to_remove_stock() {
        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = productServiceImpl.removeStockByProductId(Id.of(99, ProductId.class), 1);
        em.getTransaction().commit();

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(1));
        assertThat(messageContainer.getMessages().get(0).getMessage(), is("Product by ID '99' not found."));

        Optional<ProductEntity> changedProduct = em.createNamedQuery("ProductEntity.getById", ProductEntity.class)
                .setParameter("id", 5)
                .getResultList().stream().findFirst();
        assertTrue(changedProduct.isPresent());
        assertThat(changedProduct.get().getStock(), is(2));
    }
}
