package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.fulfilment.application.monolith.warehouses.domain.validators.WarehouseValidator;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CreateWarehouseUseCase implements CreateWarehouseOperation {

  private final WarehouseStore warehouseStore;
  private final com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver locationResolver;
  private final WarehouseValidator warehouseValidator;

  public CreateWarehouseUseCase(WarehouseStore warehouseStore, com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver locationResolver, WarehouseValidator warehouseValidator) {
    this.warehouseStore = warehouseStore;
    this.locationResolver = locationResolver;
    this.warehouseValidator = warehouseValidator;
  }

  @Override
  public void create(Warehouse warehouse) {
    if (warehouseStore.findByBusinessUnitCode(warehouse.businessUnitCode) != null) {
      throw new jakarta.ws.rs.WebApplicationException("Business Unit Code already exists.", 400);
    }

    com.fulfilment.application.monolith.warehouses.domain.models.Location loc = 
        locationResolver.resolveByIdentifier(warehouse.location);
    
    List<Warehouse> existingWarehousesInLoc = warehouseStore.getAll().stream()
        .filter(w -> w.location.equals(warehouse.location) && w.archivedAt == null)
        .toList();

    warehouseValidator.validateForCreation(warehouse, loc, existingWarehousesInLoc);

    warehouse.createdAt = java.time.LocalDateTime.now();
    warehouseStore.create(warehouse);
  }
}
