package ch.zotteljedi.onlineshop.core.builder;

import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import ch.zotteljedi.onlineshop.data.entity.PurchaseEntity;
import ch.zotteljedi.onlineshop.data.entity.PurchaseItemEntity;

public class PurchaseItemEntityBuilder {
   private final PurchaseItemEntity purchaseItemEntity = new PurchaseItemEntity();

   public PurchaseItemEntityBuilder id(Integer id) {
      purchaseItemEntity.setId(id);
      return this;
   }

   public PurchaseItemEntityBuilder unit(Integer unit) {
      purchaseItemEntity.setUnit(unit);
      return this;
   }

   public PurchaseItemEntityBuilder unitprice(Double unitprice) {
      purchaseItemEntity.setUnitprice(unitprice);
      return this;
   }

   public PurchaseItemEntityBuilder product(ProductEntity product) {
      purchaseItemEntity.setProduct(product);
      return this;
   }

   public PurchaseItemEntityBuilder purchase(PurchaseEntity purchase) {
      purchaseItemEntity.setPurchase(purchase);
      return this;
   }

   public PurchaseItemEntity build() {
      return purchaseItemEntity;
   }
}
