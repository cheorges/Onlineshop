package ch.cheorges.onlineshop.core.customer.service;

import ch.cheorges.onlineshop.common.customer.dto.Customer;
import ch.cheorges.onlineshop.common.customer.dto.CustomerId;
import ch.cheorges.onlineshop.common.customer.dto.NewCustomer;
import ch.cheorges.onlineshop.common.dto.Id;
import ch.cheorges.onlineshop.common.message.MessageContainer;
import ch.cheorges.onlineshop.core.builder.CustomerEntityBuilder;
import ch.cheorges.onlineshop.data.entity.CustomerEntity;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class CustomerServiceImplTest {
    private EntityManager em;
    private CustomerServiceImpl customerServiceImpl;

    private final CustomerEntity CUSTOMER = new CustomerEntityBuilder()
            .username("username-2")
            .firstname("firstname-2")
            .lastname("lastname-2")
            .email("firstname-2@zotteltec.ch")
            .password("password-2")
            .build();

    @Before
    public void initializeDependencies() {
        em = Persistence.createEntityManagerFactory("integration-test").createEntityManager();
        customerServiceImpl = new CustomerServiceImpl();
        customerServiceImpl.em = em;

        setUp();
    }

    private void setUp() {
        em.getTransaction().begin();
        em.persist(new CustomerEntityBuilder()
                .username("username-1")
                .firstname("firstname-1")
                .lastname("lastname-1")
                .email("firstname-1@zotteltec.ch")
                .password("password-1")
                .build());
        em.persist(CUSTOMER);
        em.getTransaction().commit();
    }

    @Test
    public void test_create_new_customer() {
        // Given
        NewCustomer newCustomer = ImmutableNewCustomer.builder()
                .username("username-3")
                .firstname("firstname-3")
                .lastname("lastname-3")
                .email("firstname-3@zotteltec.ch")
                .password("password-3")
                .build();

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = customerServiceImpl.addNewCustomer(newCustomer);
        em.getTransaction().commit();

        // Then
        assertFalse(messageContainer.hasMessages());

        List<CustomerEntity> resultList = em.createNamedQuery("CustomerEntity.get", CustomerEntity.class).getResultList();
        assertNotNull(resultList);
        assertThat(resultList.size(), is(3));
    }

    @Test
    public void test_entity_validation_messages_create_customer_with_to_short_password() {
        // Given
        NewCustomer newCustomer = ImmutableNewCustomer.builder()
                .username("username-4")
                .firstname("firstname-4")
                .lastname("lastname-4")
                .email("firstname-4@zotteltec.ch")
                .password("short")
                .build();

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = customerServiceImpl.addNewCustomer(newCustomer);
        em.getTransaction().commit();

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(1));
        assertThat(messageContainer.getMessages().get(0).getMessage(), is("Password must be between 6 and 255 characters."));
    }

    @Test
    public void test_create_new_customer_username_allready_exisit() {
        // Given
        NewCustomer newCustomer = ImmutableNewCustomer.builder()
                .username("username-1")
                .firstname("firstname-3")
                .lastname("lastname-3")
                .email("firstname-3@zotteltec.ch")
                .password("password-3")
                .build();

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = customerServiceImpl.addNewCustomer(newCustomer);
        em.getTransaction().commit();

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(1));
        assertThat(messageContainer.getMessages().get(0).getMessage(), is("Username 'username-1' already exist."));
    }

    @Test
    public void test_get_customer_by_id() {
        // When
        Optional<Customer> customer = customerServiceImpl.getCustomerById(Id.of(1, CustomerId.class));

        // Then
        assertTrue(customer.isPresent());
    }

    @Test
    public void test_get_customer_by_id_not_found() {
        // When
        Optional<Customer> customer = customerServiceImpl.getCustomerById(Id.of(99, CustomerId.class));

        // Then
        assertTrue(customer.isEmpty());
    }

    @Test
    public void test_get_customer_by_username() {
        // When
        Optional<Customer> customer = customerServiceImpl.getCustomerByUsername("username-1");

        // Then
        assertTrue(customer.isPresent());
    }

    @Test
    public void test_get_customer_by_username_not_found() {
        // When
        Optional<Customer> customer = customerServiceImpl.getCustomerByUsername("username-99");

        // Then
        assertTrue(customer.isEmpty());
    }

    @Test
    public void test_get_customerentity_by_id() {
        // When
        Optional<CustomerEntity> customer = customerServiceImpl.getCustomerEntityById(Id.of(1, CustomerId.class));

        // Then
        assertTrue(customer.isPresent());
    }

    @Test
    public void test_get_customerentity_by_id_not_found() {
        // When
        Optional<CustomerEntity> customer = customerServiceImpl.getCustomerEntityById(Id.of(99, CustomerId.class));

        // Then
        assertTrue(customer.isEmpty());
    }

    @Test
    public void test_change_customer() {
        // Given
        Customer customer = ImmutableCustomer.builder()
                .id(Id.of(1, CustomerId.class))
                .username("new-username-1")
                .firstname("new-firstname-1")
                .lastname("new-lastname-1")
                .email("new-firstname-1@zotteltec.ch")
                .password("new-password-1")
                .build();

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = customerServiceImpl.changeCustomer(customer);
        em.getTransaction().commit();

        // Then
        assertFalse(messageContainer.hasMessages());

        Optional<CustomerEntity> changedCustomer = em.createNamedQuery("CustomerEntity.getById", CustomerEntity.class)
                .setParameter("id", 1)
                .getResultList().stream().findFirst();
        assertTrue(changedCustomer.isPresent());
        assertThat(changedCustomer.get().getUsername(), is("new-username-1"));
        assertThat(changedCustomer.get().getFirstname(), is("new-firstname-1"));
        assertThat(changedCustomer.get().getLastname(), is("new-lastname-1"));
        assertThat(changedCustomer.get().getEmail(), is("new-firstname-1@zotteltec.ch"));
        assertThat(changedCustomer.get().getPassword(), is("new-password-1"));
    }

    @Test
    public void test_entity_validation_messages_change_customer_with_to_short_password() {
        Customer customer = ImmutableCustomer.builder()
                .id(Id.of(1, CustomerId.class))
                .username("username-2")
                .firstname("firstname-2")
                .lastname("lastname-2")
                .email("firstname-2@zotteltec.ch")
                .password("short")
                .build();

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = customerServiceImpl.changeCustomer(customer);
        em.getTransaction().commit();

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(1));
        assertThat(messageContainer.getMessages().get(0).getMessage(), is("Password must be between 6 and 255 characters."));
    }

    @Test
    public void test_change_customer_username_allready_used() {
        // Given
        Customer customer = ImmutableCustomer.builder()
                .id(Id.of(1, CustomerId.class))
                .username("username-2")
                .firstname("new-firstname-1")
                .lastname("new-lastname-1")
                .email("new-firstname-1@zotteltec.ch")
                .password("new-password-1")
                .build();

        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = customerServiceImpl.changeCustomer(customer);
        em.getTransaction().commit();

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(1));
        assertThat(messageContainer.getMessages().get(0).getMessage(), is("Username 'username-2' already exist."));
    }

    @Test
    public void test_customer_check_credentials() {
        // When
        boolean credentials = customerServiceImpl.checkCredentials("username-2", "password-2");

        // Then
        assertTrue(credentials);
    }

    @Test
    public void test_customer_check_credentials_failed() {
        // When
        boolean credentials = customerServiceImpl.checkCredentials("username-wrong", "password-wrong");

        // Then
        assertFalse(credentials);
    }

    @Test
    public void test_delete_customer_not_excecuted() {
        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = customerServiceImpl.deleteCustomer(Id.of(99, CustomerId.class));
        em.getTransaction().commit();

        // Then
        assertTrue(messageContainer.hasMessages());
        assertThat(messageContainer.getMessages().size(), is(1));
        assertThat(messageContainer.getMessages().get(0).getMessage(), is("Customer by ID '99' not found."));
    }

    @Test
    public void test_delete_customer() {
        // When
        em.getTransaction().begin();
        MessageContainer messageContainer = customerServiceImpl.deleteCustomer(Id.of(1, CustomerId.class));
        em.getTransaction().commit();

        // Then
        assertFalse(messageContainer.hasMessages());
    }

    @Test
    public void test_customer_representaiton() {
        // When
        String representation = customerServiceImpl.buildCustomerRepresentation(new CustomerEntityBuilder()
                .firstname("firstname")
                .lastname("lastname")
                .build());

        // Then
        assertThat(representation, is("firstname lastname"));
    }
}
