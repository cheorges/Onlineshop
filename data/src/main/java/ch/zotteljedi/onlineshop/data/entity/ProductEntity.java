package ch.zotteljedi.onlineshop.data.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "ProductEntity.get",
        query = "select p from ProductEntity p")
@NamedQuery(name = "ProductEntity.getById",
        query = "select p from ProductEntity p where p.id = :id")
@NamedQuery(name = "ProductEntity.getBySeller",
        query = "select p from ProductEntity p where p.seller = :seller")
@NamedQuery(name = "ProductEntity.getByStockNotEmpty",
        query = "select p from ProductEntity p where p.stock > 0")
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

    @Column(name = "unitprice", nullable = false, precision = 2)
    @NotNull(message = "Price may not be blank.")
    private Double unitprice;

    @Column(name = "stock", nullable = false)
    @NotNull(message = "Stock may not be blank.")
    private Integer stock;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "photo", nullable = false)
    @NotNull(message = "Photo may not be blank.")
    private byte[] photo;

    @ManyToOne
    @NotNull(message = "Seller may not be blank.")
    private CustomerEntity seller;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<PurchaseItemEntity> purchaseItemEntities = new ArrayList<>();

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

    public Double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(Double price) {
        this.unitprice = price;
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

    public List<PurchaseItemEntity> getPurchaseItemEntities() {
        return purchaseItemEntities;
    }

    public void setPurchaseItemEntities(List<PurchaseItemEntity> purchaseItemEntities) {
        this.purchaseItemEntities = purchaseItemEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity)) return false;
        ProductEntity that = (ProductEntity) o;
        return id == that.id &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(unitprice, that.unitprice) &&
                Objects.equals(stock, that.stock) &&
                Arrays.equals(photo, that.photo) &&
                Objects.equals(seller, that.seller) &&
                Objects.equals(purchaseItemEntities, that.purchaseItemEntities);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, description, unitprice, stock, seller, purchaseItemEntities);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", unitprice=" + unitprice +
                ", stock=" + stock +
                ", photo=" + Arrays.toString(photo) +
                ", seller=" + seller +
                ", purchaseItemEntities=" + purchaseItemEntities +
                '}';
    }

}
