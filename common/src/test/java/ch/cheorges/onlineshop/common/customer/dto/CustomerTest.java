package ch.cheorges.onlineshop.common.customer.dto;

import ch.cheorges.onlineshop.common.dto.Id;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class CustomerTest {

    @Test
    public void test_create_customor_dto() {
        // Given
        Customer customer = ImmutableCustomer.builder()
                .id(Id.of(1, CustomerId.class))
                .username("username")
                .firstname("firstname")
                .lastname("lastname")
                .email("email")
                .password("password")
                .build();

        // Then
        assertNotNull(customer);
        assertThat(customer.getId().getValue(), is(1));
        assertThat(customer.getUsername(), is("username"));
        assertThat(customer.getFirstname(), is("firstname"));
        assertThat(customer.getLastname(), is("lastname"));
        assertThat(customer.getEmail(), is("email"));
        assertThat(customer.getPassword(), is("password"));
    }

}