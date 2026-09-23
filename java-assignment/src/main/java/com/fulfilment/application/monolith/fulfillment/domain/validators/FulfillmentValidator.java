package com.fulfilment.application.monolith.fulfillment.domain.validators;

import com.fulfilment.application.monolith.fulfillment.domain.ports.FulfillmentStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class FulfillmentValidator {

  private final FulfillmentStore store;

  public FulfillmentValidator(FulfillmentStore store) {
    this.store = store;
  }

  public void validateAssociation(Long storeId, Long productId, String warehouseBuCode) {
    long productWarehouseCountForStore = store.getProductWarehouseCountForStore(storeId, productId);
    if (productWarehouseCountForStore >= 2) {
      throw new WebApplicationException("Product can be fulfilled by a maximum of 2 different warehouses per store.", 400);
    }

    List<String> uniqueWarehousesForStore = store.getUniqueWarehousesForStore(storeId);
    boolean alreadyFulfillingStore = store.isAlreadyFulfillingStore(storeId, warehouseBuCode);
    if (!alreadyFulfillingStore && uniqueWarehousesForStore.size() >= 3) {
      throw new WebApplicationException("Store can be fulfilled by a maximum of 3 different warehouses.", 400);
    }

    List<Long> uniqueProductsForWarehouse = store.getUniqueProductsForWarehouse(warehouseBuCode);
    boolean alreadyStoringProduct = store.isAlreadyStoringProduct(warehouseBuCode, productId);
    if (!alreadyStoringProduct && uniqueProductsForWarehouse.size() >= 5) {
      throw new WebApplicationException("Warehouse can store maximally 5 types of products.", 400);
    }

    if (store.exists(storeId, productId, warehouseBuCode)) {
      throw new WebApplicationException("Association already exists.", 400);
    }
  }
}
