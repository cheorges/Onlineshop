package ch.zotteljedi.onlineshop.customer.dto;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
public interface Customer extends Serializable {
    Integer getId();
    String getUsername();
    String getFirstname();
    String getLastname();
    String getEmail();
    String getPassword();

    @Value.Derived
    default String getRepresentation() {
        return getFirstname() + " " + getLastname();
    }
}
