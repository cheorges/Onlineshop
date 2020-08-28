package ch.zotteljedi.onlineshop.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@NamedQuery(name = "CustomerEntity.get",
        query = "select c from CustomerEntity c")
@NamedQuery(name = "CustomerEntity.getByUsername",
        query = "select c from CustomerEntity c where c.username = :username")
@NamedQuery(name = "CustomerEntity.getByUsernameAndPassword",
        query = "select c from CustomerEntity c where c.username = :username and c.password = :password")
public class CustomerEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CustomerEntity))
            return false;
        CustomerEntity that = (CustomerEntity) o;
        return getId() == that.getId() &&
              getUsername().equals(that.getUsername()) &&
              getFirstname().equals(that.getFirstname()) &&
              getLastname().equals(that.getLastname()) &&
              getEmail().equals(that.getEmail()) &&
              getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getFirstname(), getLastname(), getEmail(), getPassword());
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", firstname='" + firstname + '\'' +
              ", lastname='" + lastname + '\'' +
              ", email='" + email + '\'' +
              ", password='" + password + '\'' +
              '}';
    }
}
