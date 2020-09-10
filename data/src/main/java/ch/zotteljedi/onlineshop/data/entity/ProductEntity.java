package ch.zotteljedi.onlineshop.data.entity;

import javax.persistence.*;
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
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "price", nullable = false, precision = 2)
    private Double price;


    @Basic(fetch=FetchType.LAZY)
    @Column(name = "photo", nullable = false)
    private byte[] photo;

    @ManyToOne
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
                Arrays.equals(photo, that.photo) &&
                seller.equals(that.seller);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, description, price, seller);
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
                ", photo=" + Arrays.toString(photo) +
                ", seller=" + seller +
                '}';
    }

}
