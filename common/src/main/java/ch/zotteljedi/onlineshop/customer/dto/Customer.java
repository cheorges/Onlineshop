package ch.zotteljedi.onlineshop.customer.dto;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
public interface Customer extends Serializable {
    String getUsername();
    String getFirstname();
    String getLastname();
    String getEmail();
    String getPassword();
    @Value.Default
    default Boolean getAuthenticated() {
        return Boolean.FALSE;
    }
}
