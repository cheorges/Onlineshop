package ch.zotteljedi.onlineshop.customer.dto;

import org.immutables.value.Value;

@Value.Immutable
public interface Customer {
    String getUsername();
    String getFirstname();
    String getLastname();
    String getEmail();
    String getPassword();
}
