package ch.zotteljedi.onlineshop.core.purchase.service;

import ch.zotteljedi.onlineshop.data.entity.ProductEntity;
import ch.zotteljedi.onlineshop.data.entity.PurchaseEntity;
import ch.zotteljedi.onlineshop.data.entity.PurchaseItemEntity;

public class PurchaseItemEntityBuilder {
   private final PurchaseItemEntity purchaseItemEntity = new PurchaseItemEntity();

   public PurchaseItemEntityBuilder id(Integer id) {
      purchaseItemEntity.setId(id);
      return this;
   }

   public PurchaseItemEntityBuilder boughtAt(Integer unit) {
      purchaseItemEntity.setUnit(unit);
      return this;
   }

   public PurchaseItemEntityBuilder buyer(Double unitprice) {
      purchaseItemEntity.setUnitprice(unitprice);
      return this;
   }

   public PurchaseItemEntityBuilder buyer(ProductEntity product) {
      purchaseItemEntity.setProduct(product);
      return this;
   }

   public PurchaseItemEntityBuilder buyer(PurchaseEntity purchase) {
      purchaseItemEntity.setPurchase(purchase);
      return this;
   }

   public PurchaseItemEntity build() {
      return purchaseItemEntity;
   }
}
