package com.fulfilment.application.monolith.fulfillment.domain.ports;

import com.fulfilment.application.monolith.fulfillment.domain.models.FulfillmentAssociation;
import java.util.List;

public interface FulfillmentStore {
  void create(FulfillmentAssociation association);
  long getProductWarehouseCountForStore(Long storeId, Long productId);
  List<String> getUniqueWarehousesForStore(Long storeId);
  List<Long> getUniqueProductsForWarehouse(String warehouseBusinessUnitCode);
  boolean exists(Long storeId, Long productId, String warehouseBusinessUnitCode);
  boolean isAlreadyFulfillingStore(Long storeId, String warehouseBusinessUnitCode);
  boolean isAlreadyStoringProduct(String warehouseBusinessUnitCode, Long productId);
}
