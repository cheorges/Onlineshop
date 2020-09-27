package ch.zotteljedi.onlineshop.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@NamedQuery(name = "PurchaseEntitiy.get",
        query = "select p from PurchaseEntitiy p")
@NamedQuery(name = "PurchaseEntitiy.getByBuyer",
        query = "select p from PurchaseEntitiy p where p.buyer = :buyer")
@NamedQuery(name = "PurchaseEntitiy.getById",
        query = "select p from PurchaseEntitiy p where p.id = :id")
public class PurchaseEntitiy {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "boughtAt", nullable = false)
    private LocalDate boughtAt;

    @ManyToOne
    @NotNull(message = "Buyer may not be blank.")
    private CustomerEntity buyer;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseEntitiy)) return false;
        PurchaseEntitiy that = (PurchaseEntitiy) o;
        return id == that.id &&
                boughtAt.equals(that.boughtAt) &&
                buyer.equals(that.buyer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, boughtAt, buyer);
    }

    @Override
    public String toString() {
        return "PurchaseEntitiy{" +
                "id=" + id +
                ", boughtAt=" + boughtAt +
                ", buyer=" + buyer +
                '}';
    }
}
