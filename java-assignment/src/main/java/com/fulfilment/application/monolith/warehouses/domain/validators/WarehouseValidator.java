package com.fulfilment.application.monolith.warehouses.domain.validators;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class WarehouseValidator {

  public void validateForCreation(Warehouse warehouse, Location loc, List<Warehouse> existingWarehousesInLoc) {
    if (loc == null) {
      throw new WebApplicationException("Location is invalid.", 400);
    }

    if (existingWarehousesInLoc.size() >= loc.maxNumberOfWarehouses) {
      throw new WebApplicationException("Maximum number of warehouses reached for location.", 400);
    }

    int currentTotalCapacity = existingWarehousesInLoc.stream().mapToInt(w -> w.capacity).sum();
    if (currentTotalCapacity + warehouse.capacity > loc.maxCapacity) {
      throw new WebApplicationException("Warehouse capacity exceeds location max capacity.", 400);
    }

    if (warehouse.stock > warehouse.capacity) {
      throw new WebApplicationException("Stock cannot exceed capacity.", 400);
    }
  }

  public void validateForReplacement(Warehouse newWarehouse, Warehouse oldWarehouse) {
    if (oldWarehouse == null || oldWarehouse.archivedAt != null) {
      throw new WebApplicationException("Active warehouse to replace not found.", 404);
    }

    if (newWarehouse.capacity < oldWarehouse.stock) {
      throw new WebApplicationException("New warehouse capacity cannot accommodate old stock.", 400);
    }

    if (!newWarehouse.stock.equals(oldWarehouse.stock)) {
      throw new WebApplicationException("New warehouse stock must match old warehouse stock.", 400);
    }
  }
}
