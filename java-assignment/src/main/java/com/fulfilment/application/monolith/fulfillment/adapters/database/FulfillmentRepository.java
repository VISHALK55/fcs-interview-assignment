package com.fulfilment.application.monolith.fulfillment.adapters.database;

import com.fulfilment.application.monolith.fulfillment.domain.models.FulfillmentAssociation;
import com.fulfilment.application.monolith.fulfillment.domain.ports.FulfillmentStore;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FulfillmentRepository implements FulfillmentStore {

  @Override
  public void create(FulfillmentAssociation association) {
    DbStoreProductWarehouse dbEntity = new DbStoreProductWarehouse(association.storeId, association.productId, association.warehouseBusinessUnitCode);
    dbEntity.persist();
  }

  @Override
  public long getProductWarehouseCountForStore(Long storeId, Long productId) {
    return DbStoreProductWarehouse.find("storeId = ?1 and productId = ?2", storeId, productId).count();
  }

  @Override
  public List<String> getUniqueWarehousesForStore(Long storeId) {
    return DbStoreProductWarehouse.find("storeId = ?1", storeId).stream()
        .map(spw -> ((DbStoreProductWarehouse) spw).warehouseBusinessUnitCode)
        .distinct()
        .toList();
  }

  @Override
  public List<Long> getUniqueProductsForWarehouse(String warehouseBusinessUnitCode) {
    return DbStoreProductWarehouse.find("warehouseBusinessUnitCode = ?1", warehouseBusinessUnitCode).stream()
        .map(spw -> ((DbStoreProductWarehouse) spw).productId)
        .distinct()
        .toList();
  }

  @Override
  public boolean exists(Long storeId, Long productId, String warehouseBusinessUnitCode) {
    return DbStoreProductWarehouse.count("storeId = ?1 and productId = ?2 and warehouseBusinessUnitCode = ?3", storeId, productId, warehouseBusinessUnitCode) > 0;
  }

  @Override
  public boolean isAlreadyFulfillingStore(Long storeId, String warehouseBusinessUnitCode) {
    return DbStoreProductWarehouse.count("storeId = ?1 and warehouseBusinessUnitCode = ?2", storeId, warehouseBusinessUnitCode) > 0;
  }

  @Override
  public boolean isAlreadyStoringProduct(String warehouseBusinessUnitCode, Long productId) {
    return DbStoreProductWarehouse.count("warehouseBusinessUnitCode = ?1 and productId = ?2", warehouseBusinessUnitCode, productId) > 0;
  }
}
