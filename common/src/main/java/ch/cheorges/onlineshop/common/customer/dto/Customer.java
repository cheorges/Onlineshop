package ch.cheorges.onlineshop.common.customer.dto;

import ch.cheorges.onlineshop.common.dto.Entity;
import org.immutables.value.Value;

@Value.Immutable
public interface Customer extends Entity<CustomerId> {
    String getUsername();

    String getFirstname();

    String getLastname();

    String getEmail();

    String getPassword();
}
