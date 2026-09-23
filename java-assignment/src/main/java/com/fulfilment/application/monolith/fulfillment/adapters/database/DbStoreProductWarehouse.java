package com.fulfilment.application.monolith.fulfillment.adapters.database;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "StoreProductWarehouse")
public class DbStoreProductWarehouse extends PanacheEntity {

  public Long storeId;
  public Long productId;
  public String warehouseBusinessUnitCode;

  public DbStoreProductWarehouse() {}

  public DbStoreProductWarehouse(Long storeId, Long productId, String warehouseBusinessUnitCode) {
    this.storeId = storeId;
    this.productId = productId;
    this.warehouseBusinessUnitCode = warehouseBusinessUnitCode;
  }
}
