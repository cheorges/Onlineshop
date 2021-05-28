package ch.cheorges.onlineshop.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "PurchaseEntitiy.get",
        query = "select p from PurchaseEntity p")
@NamedQuery(name = "PurchaseEntitiy.getByBuyer",
        query = "select p from PurchaseEntity p where p.buyer = :buyer")
@NamedQuery(name = "PurchaseEntitiy.getById",
        query = "select p from PurchaseEntity p where p.id = :id")
public class PurchaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "boughtAt", nullable = false)
    @NotNull(message = "Purchasedate may not be blank.")
    private LocalDate boughtAt;

    @ManyToOne
    @NotNull(message = "Buyer may not be blank.")
    private CustomerEntity buyer;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.REMOVE)
    private List<PurchaseItemEntity> purchaseItemEntities = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(LocalDate boughtAt) {
        this.boughtAt = boughtAt;
    }

    public CustomerEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(CustomerEntity buyer) {
        this.buyer = buyer;
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
        if (!(o instanceof PurchaseEntity)) return false;
        PurchaseEntity that = (PurchaseEntity) o;
        return id == that.id &&
                Objects.equals(boughtAt, that.boughtAt) &&
                Objects.equals(buyer, that.buyer) &&
                Objects.equals(purchaseItemEntities, that.purchaseItemEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, boughtAt, buyer, purchaseItemEntities);
    }

    @Override
    public String toString() {
        return "PurchaseEntity{" +
                "id=" + id +
                ", boughtAt=" + boughtAt +
                ", buyer=" + buyer +
                ", purchaseItemEntities=" + purchaseItemEntities +
                '}';
    }
}
