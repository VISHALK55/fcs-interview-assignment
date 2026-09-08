package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateWarehouseUseCase implements CreateWarehouseOperation {

  private final WarehouseStore warehouseStore;
  private final com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver locationResolver;

  public CreateWarehouseUseCase(WarehouseStore warehouseStore, com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver locationResolver) {
    this.warehouseStore = warehouseStore;
    this.locationResolver = locationResolver;
  }

  @Override
  public void create(Warehouse warehouse) {
    // Validation 1: Ensure business unit code doesn't exist
    if (warehouseStore.findByBusinessUnitCode(warehouse.businessUnitCode) != null) {
      throw new jakarta.ws.rs.WebApplicationException("Business Unit Code already exists.", 400);
    }

    // Validation 2: Ensure location is valid
    com.fulfilment.application.monolith.warehouses.domain.models.Location loc = 
        locationResolver.resolveByIdentifier(warehouse.location);
    if (loc == null) {
      throw new jakarta.ws.rs.WebApplicationException("Location is invalid.", 400);
    }

    // Validation 3 & 4: Capacity and warehouse count check
    java.util.List<Warehouse> existingWarehousesInLoc = warehouseStore.getAll().stream()
        .filter(w -> w.location.equals(warehouse.location) && w.archivedAt == null)
        .toList();

    if (existingWarehousesInLoc.size() >= loc.maxNumberOfWarehouses) {
      throw new jakarta.ws.rs.WebApplicationException("Maximum number of warehouses reached for location.", 400);
    }

    int currentTotalCapacity = existingWarehousesInLoc.stream().mapToInt(w -> w.capacity).sum();
    if (currentTotalCapacity + warehouse.capacity > loc.maxCapacity) {
      throw new jakarta.ws.rs.WebApplicationException("Warehouse capacity exceeds location max capacity.", 400);
    }

    if (warehouse.stock > warehouse.capacity) {
      throw new jakarta.ws.rs.WebApplicationException("Stock cannot exceed capacity.", 400);
    }

    warehouse.createdAt = java.time.LocalDateTime.now();

    // if all went well, create the warehouse
    warehouseStore.create(warehouse);
  }
}
