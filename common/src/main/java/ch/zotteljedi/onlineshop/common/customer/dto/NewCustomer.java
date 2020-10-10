package ch.zotteljedi.onlineshop.common.customer.dto;

import ch.zotteljedi.onlineshop.common.dto.ValueObject;
import org.immutables.value.Value;

@Value.Immutable
public interface NewCustomer extends ValueObject {
    String getUsername();

    String getFirstname();

    String getLastname();

    String getEmail();

    String getPassword();
}
