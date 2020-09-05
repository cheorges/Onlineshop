package ch.zotteljedi.onlineshop.customer.dto;

import ch.zotteljedi.onlineshop.common.dto.Entity;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@Value.Immutable
public interface Customer extends Entity<CustomerId> {
    String getUsername();
    String getFirstname();
    String getLastname();
    String getEmail();
    String getPassword();
}
