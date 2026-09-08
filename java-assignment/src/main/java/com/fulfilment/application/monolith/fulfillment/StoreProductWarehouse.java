package com.fulfilment.application.monolith.fulfillment;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class StoreProductWarehouse extends PanacheEntity {

  public Long storeId;
  public Long productId;
  public String warehouseBusinessUnitCode;

  public StoreProductWarehouse() {}

  public StoreProductWarehouse(Long storeId, Long productId, String warehouseBusinessUnitCode) {
    this.storeId = storeId;
    this.productId = productId;
    this.warehouseBusinessUnitCode = warehouseBusinessUnitCode;
  }
}
