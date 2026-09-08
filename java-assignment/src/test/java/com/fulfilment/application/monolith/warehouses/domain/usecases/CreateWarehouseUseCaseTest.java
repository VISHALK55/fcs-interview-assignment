package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CreateWarehouseUseCaseTest {

  private WarehouseStore warehouseStore;
  private LocationResolver locationResolver;
  private CreateWarehouseUseCase createWarehouseUseCase;

  @BeforeEach
  void setUp() {
    warehouseStore = mock(WarehouseStore.class);
    locationResolver = mock(LocationResolver.class);
    createWarehouseUseCase = new CreateWarehouseUseCase(warehouseStore, locationResolver);
  }

  @Test
  void testCreateWarehouseSuccessfully() {
    Warehouse w = new Warehouse();
    w.businessUnitCode = "BU1";
    w.location = "LOC1";
    w.capacity = 10;
    w.stock = 5;

    when(warehouseStore.findByBusinessUnitCode("BU1")).thenReturn(null);
    when(locationResolver.resolveByIdentifier("LOC1")).thenReturn(new Location("LOC1", 2, 20));
    when(warehouseStore.getAll()).thenReturn(List.of());

    createWarehouseUseCase.create(w);

    verify(warehouseStore, times(1)).create(w);
  }

  @Test
  void testCreateFailsIfBUCodeExists() {
    Warehouse w = new Warehouse();
    w.businessUnitCode = "BU1";
    when(warehouseStore.findByBusinessUnitCode("BU1")).thenReturn(new Warehouse());

    assertThrows(WebApplicationException.class, () -> createWarehouseUseCase.create(w));
  }
}
