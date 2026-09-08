package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReplaceWarehouseUseCase implements ReplaceWarehouseOperation {

  private final WarehouseStore warehouseStore;
  private final com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation archiveWarehouseOperation;

  public ReplaceWarehouseUseCase(WarehouseStore warehouseStore, com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation archiveWarehouseOperation) {
    this.warehouseStore = warehouseStore;
    this.archiveWarehouseOperation = archiveWarehouseOperation;
  }

  @Override
  public void replace(Warehouse newWarehouse) {
    Warehouse oldWarehouse = warehouseStore.findByBusinessUnitCode(newWarehouse.businessUnitCode);
    if (oldWarehouse == null || oldWarehouse.archivedAt != null) {
      throw new jakarta.ws.rs.WebApplicationException("Active warehouse to replace not found.", 404);
    }

    if (newWarehouse.capacity < oldWarehouse.stock) {
      throw new jakarta.ws.rs.WebApplicationException("New warehouse capacity cannot accommodate old stock.", 400);
    }

    if (!newWarehouse.stock.equals(oldWarehouse.stock)) {
      throw new jakarta.ws.rs.WebApplicationException("New warehouse stock must match old warehouse stock.", 400);
    }

    archiveWarehouseOperation.archive(oldWarehouse);
    
    newWarehouse.createdAt = java.time.LocalDateTime.now();
    warehouseStore.create(newWarehouse);
  }
}
