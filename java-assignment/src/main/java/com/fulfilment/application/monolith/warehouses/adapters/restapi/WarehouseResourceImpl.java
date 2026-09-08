package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.warehouse.api.WarehouseResource;
import com.warehouse.api.beans.Warehouse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RequestScoped
public class WarehouseResourceImpl implements WarehouseResource {

  @Inject private WarehouseRepository warehouseRepository;
  @Inject private com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation createWarehouseOperation;
  @Inject private com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation archiveWarehouseOperation;
  @Inject private com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation replaceWarehouseOperation;

  @Override
  public List<Warehouse> listAllWarehousesUnits() {
    return warehouseRepository.getAll().stream().map(this::toWarehouseResponse).toList();
  }

  @Override
  @jakarta.transaction.Transactional
  public Warehouse createANewWarehouseUnit(@NotNull Warehouse data) {
    var domainWarehouse = toDomainWarehouse(data);
    createWarehouseOperation.create(domainWarehouse);
    return toWarehouseResponse(warehouseRepository.findByBusinessUnitCode(data.getBusinessUnitCode()));
  }

  @Override
  public Warehouse getAWarehouseUnitByID(String id) {
    var warehouse = warehouseRepository.findByBusinessUnitCode(id);
    if (warehouse == null) {
      throw new jakarta.ws.rs.WebApplicationException("Warehouse not found", 404);
    }
    return toWarehouseResponse(warehouse);
  }

  @Override
  @jakarta.transaction.Transactional
  public void archiveAWarehouseUnitByID(String id) {
    var warehouse = warehouseRepository.findByBusinessUnitCode(id);
    if (warehouse == null) {
      throw new jakarta.ws.rs.WebApplicationException("Warehouse not found", 404);
    }
    archiveWarehouseOperation.archive(warehouse);
  }

  @Override
  @jakarta.transaction.Transactional
  public Warehouse replaceTheCurrentActiveWarehouse(
      String businessUnitCode, @NotNull Warehouse data) {
    if (!businessUnitCode.equals(data.getBusinessUnitCode())) {
      throw new jakarta.ws.rs.WebApplicationException("Business unit code mismatch", 400);
    }
    var domainWarehouse = toDomainWarehouse(data);
    replaceWarehouseOperation.replace(domainWarehouse);
    return toWarehouseResponse(warehouseRepository.findByBusinessUnitCode(businessUnitCode));
  }

  private com.fulfilment.application.monolith.warehouses.domain.models.Warehouse toDomainWarehouse(Warehouse warehouse) {
    var domain = new com.fulfilment.application.monolith.warehouses.domain.models.Warehouse();
    domain.businessUnitCode = warehouse.getBusinessUnitCode();
    domain.location = warehouse.getLocation();
    domain.capacity = warehouse.getCapacity();
    domain.stock = warehouse.getStock();
    return domain;
  }

  private Warehouse toWarehouseResponse(
      com.fulfilment.application.monolith.warehouses.domain.models.Warehouse warehouse) {
    var response = new Warehouse();
    response.setBusinessUnitCode(warehouse.businessUnitCode);
    response.setLocation(warehouse.location);
    response.setCapacity(warehouse.capacity);
    response.setStock(warehouse.stock);

    return response;
  }
}
