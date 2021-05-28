package ch.cheorges.onlineshop.common.customer.dto;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class NewCustomerTest {

    @Test
    public void test_create_new_customor_dto() {
        // Given
        NewCustomer customer = ImmutableNewCustomer.builder()
                .username("username")
                .firstname("firstname")
                .lastname("lastname")
                .email("email")
                .password("password")
                .build();

        // Then
        assertNotNull(customer);
        assertThat(customer.getUsername(), is("username"));
        assertThat(customer.getFirstname(), is("firstname"));
        assertThat(customer.getLastname(), is("lastname"));
        assertThat(customer.getEmail(), is("email"));
        assertThat(customer.getPassword(), is("password"));
    }

}