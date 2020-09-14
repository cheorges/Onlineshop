package ch.zotteljedi.onlineshop.data.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Objects;

@Entity
@NamedQuery(name = "ProductEntity.get",
        query = "select p from ProductEntity p")
@NamedQuery(name = "ProductEntity.getById",
        query = "select p from ProductEntity p where p.id = :id")
@NamedQuery(name = "ProductEntity.getBySeller",
        query = "select p from ProductEntity p where p.seller = :seller")
public class ProductEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "title", nullable = false)
    @NotEmpty(message = "Title may not be empty.")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters.")
    private String title;

    @Column(name = "description", length = 1000)
    @Size(max = 1000, message = "Description should not be greater than 1000 characters.")
    private String description;

    @Column(name = "price", nullable = false, precision = 2)
    @NotNull(message = "Price may not be blank.")
    private Double price;

    @Column(name = "stock", nullable = false)
    @NotNull(message = "Stock may not be blank.")
    private Integer stock;

    @Basic(fetch=FetchType.LAZY)
    @Column(name = "photo", nullable = false)
    @NotNull(message = "Photo may not be blank.")
    private byte[] photo;

    @ManyToOne
    @NotNull(message = "Seller may not be blank.")
    private CustomerEntity seller;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public CustomerEntity getSeller() {
        return seller;
    }

    public void setSeller(CustomerEntity seller) {
        this.seller = seller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity)) return false;
        ProductEntity that = (ProductEntity) o;
        return id == that.id &&
                title.equals(that.title) &&
                Objects.equals(description, that.description) &&
                price.equals(that.price) &&
                stock.equals(that.stock) &&
                Arrays.equals(photo, that.photo) &&
                seller.equals(that.seller);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, description, price, stock, seller);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", photo=" + Arrays.toString(photo) +
                ", seller=" + seller +
                '}';
    }

}
