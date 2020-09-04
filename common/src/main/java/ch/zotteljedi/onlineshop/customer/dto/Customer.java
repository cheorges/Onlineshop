package ch.zotteljedi.onlineshop.customer.dto;

import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@Value.Immutable
public interface Customer extends Serializable {
    @Nullable
    Integer getId();
    String getUsername();
    String getFirstname();
    String getLastname();
    String getEmail();
    String getPassword();
}
