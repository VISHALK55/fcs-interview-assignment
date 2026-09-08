package com.fulfilment.application.monolith.fulfillment;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class FulfillmentService {

  @Transactional
  public StoreProductWarehouse associate(Long storeId, Long productId, String warehouseBuCode) {
    // 1. Each Product can be fulfilled by a maximum of 2 different Warehouses per Store
    long productWarehouseCountForStore = StoreProductWarehouse.find("storeId = ?1 and productId = ?2", storeId, productId).count();
    if (productWarehouseCountForStore >= 2) {
      throw new WebApplicationException("Product can be fulfilled by a maximum of 2 different warehouses per store.", 400);
    }

    // 2. Each Store can be fulfilled by a maximum of 3 different Warehouses
    long uniqueWarehousesForStore = StoreProductWarehouse.find("storeId = ?1", storeId)
        .stream()
        .map(spw -> ((StoreProductWarehouse) spw).warehouseBusinessUnitCode)
        .distinct()
        .count();
        
    // Wait, if this warehouse is already fulfilling for this store, we don't increase the count.
    boolean alreadyFulfillingStore = StoreProductWarehouse.count("storeId = ?1 and warehouseBusinessUnitCode = ?2", storeId, warehouseBuCode) > 0;
    if (!alreadyFulfillingStore && uniqueWarehousesForStore >= 3) {
      throw new WebApplicationException("Store can be fulfilled by a maximum of 3 different warehouses.", 400);
    }

    // 3. Each Warehouse can store maximally 5 types of Products
    long uniqueProductsForWarehouse = StoreProductWarehouse.find("warehouseBusinessUnitCode = ?1", warehouseBuCode)
        .stream()
        .map(spw -> ((StoreProductWarehouse) spw).productId)
        .distinct()
        .count();

    boolean alreadyStoringProduct = StoreProductWarehouse.count("warehouseBusinessUnitCode = ?1 and productId = ?2", warehouseBuCode, productId) > 0;
    if (!alreadyStoringProduct && uniqueProductsForWarehouse >= 5) {
      throw new WebApplicationException("Warehouse can store maximally 5 types of products.", 400);
    }

    // Check if exactly this combination already exists
    if (StoreProductWarehouse.count("storeId = ?1 and productId = ?2 and warehouseBusinessUnitCode = ?3", storeId, productId, warehouseBuCode) > 0) {
      throw new WebApplicationException("Association already exists.", 400);
    }

    StoreProductWarehouse spw = new StoreProductWarehouse(storeId, productId, warehouseBuCode);
    spw.persist();
    return spw;
  }
}
