package ch.zotteljedi.onlineshop.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NamedQuery(name = "PurchaseItemEntity.get",
        query = "select p from PurchaseItemEntity p")
@NamedQuery(name = "PurchaseItemEntity.getById",
        query = "select p from PurchaseItemEntity p where p.id = :id")
@NamedQuery(name = "PurchaseItemEntity.getByPruchase",
        query = "select p from PurchaseItemEntity p where p.purchase = :purchase")
@NamedQuery(name = "PurchaseItemEntity.getByProduct",
        query = "select p from PurchaseItemEntity p where p.product = :product")
public class PurchaseItemEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "unitprice", nullable = false, precision = 2)
    @NotNull(message = "Price may not be blank.")
    private Double unitprice;

    @Column(name = "unit", nullable = false)
    @NotNull(message = "Unit may not be blank.")
    private Integer unit;

    @ManyToOne
    @NotNull(message = "Purchase may not be blank.")
    private PurchaseEntity purchase;

    @ManyToOne
    @NotNull(message = "Product may not be blank.")
    private ProductEntity product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public PurchaseEntity getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseEntity purchase) {
        this.purchase = purchase;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseItemEntity)) return false;
        PurchaseItemEntity that = (PurchaseItemEntity) o;
        return id == that.id &&
                unitprice.equals(that.unitprice) &&
                unit.equals(that.unit) &&
                purchase.equals(that.purchase) &&
                product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unitprice, unit, purchase, product);
    }

    @Override
    public String toString() {
        return "PurchaseItemEntity{" +
                "id=" + id +
                ", unitprice=" + unitprice +
                ", unit=" + unit +
                ", purchase=" + purchase +
                ", product=" + product +
                '}';
    }
}
