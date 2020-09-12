package ch.zotteljedi.onlineshop.common.customer.dto;

import ch.zotteljedi.onlineshop.common.dto.Id;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class CustomerIdTest {

    @Test
    public void test_create_customer_id() {
        // Given
        CustomerId id = Id.of(1, CustomerId.class);

        // Then
        assertNotNull(id);
        assertThat(id.getValue(), is(1));
    }

}