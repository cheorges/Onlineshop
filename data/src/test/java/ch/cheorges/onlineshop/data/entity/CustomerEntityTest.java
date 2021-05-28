package ch.cheorges.onlineshop.data.entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class CustomerEntityTest {

    @Test
    public void test_init_customer_entity() {
        // Given
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setUsername("admin");
        customerEntity.setFirstname("Administrator");
        customerEntity.setLastname("Zotteltec");
        customerEntity.setEmail("admin@zotteltec.ch");
        customerEntity.setPassword("password");

        // Then
        assertThat(customerEntity.getId(), is(1));
        assertThat(customerEntity.getUsername(), is("admin"));
        assertThat(customerEntity.getFirstname(), is("Administrator"));
        assertThat(customerEntity.getLastname(), is("Zotteltec"));
        assertThat(customerEntity.getEmail(), is("admin@zotteltec.ch"));
        assertThat(customerEntity.getPassword(), is("password"));
    }

}