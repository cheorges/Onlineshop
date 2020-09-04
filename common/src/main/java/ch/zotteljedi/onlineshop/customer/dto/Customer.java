package ch.zotteljedi.onlineshop.customer.dto;

import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

//@Value.Immutable
//public interface Customer extends Serializable {
//    @Nullable
//    Integer getId();
//    String getUsername();
//    String getFirstname();
//    String getLastname();
//    String getEmail();
//    String getPassword();
//
//    @Value.Derived
//    default String getRepresentation() {
//        return getFirstname() + " " + getLastname();
//    }
//}

public class Customer implements Serializable {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public String getRepresentation() {
        return this.firstname + " " + this.lastname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
