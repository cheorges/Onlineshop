package ch.cheorges.onlineshop.data.entity;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "CustomerEntity.get",
        query = "select c from CustomerEntity c")
@NamedQuery(name = "CustomerEntity.getByUsername",
        query = "select c from CustomerEntity c where c.username = :username")
@NamedQuery(name = "CustomerEntity.getById",
        query = "select c from CustomerEntity c where c.id = :id")
@NamedQuery(name = "CustomerEntity.getByUsernameAndPassword",
        query = "select c from CustomerEntity c where c.username = :username and c.password = :password")
public class CustomerEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty(message = "Username may not be empty.")
    @Size(min = 1, max = 255, message = "Username must be between 1 and 255 characters.")
    private String username;

    @Column(name = "firstname", nullable = false)
    @NotEmpty(message = "Firstname may not be empty.")
    @Size(min = 1, max = 255, message = "Firstname must be between 1 and 255 characters.")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @NotEmpty(message = "Lastname may not be empty.")
    @Size(min = 1, max = 255, message = "Lastname must be between 1 and 255 characters.")
    private String lastname;

    @Column(name = "email", nullable = false)
    @NotEmpty(message = "Email may not be empty.")
    @Email(message = "Is not a valid email.")
    @Size(min = 1, max = 255, message = "Email must be between 1 and 255 characters.")
    private String email;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Password may not be empty.")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters.")
    private String password;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.REMOVE)
    private List<ProductEntity> products = new ArrayList<>();

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.REMOVE)
    private List<PurchaseEntity> purchases = new ArrayList<>();

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

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseEntity> purchase) {
        this.purchases = purchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerEntity)) return false;
        CustomerEntity that = (CustomerEntity) o;
        return id == that.id &&
                Objects.equals(username, that.username) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(products, that.products) &&
                Objects.equals(purchases, that.purchases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstname, lastname, email, password, products, purchases);
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
                ", products=" + products +
                ", purchase=" + purchases +
                '}';
    }
}
