package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.ws.rs.WebApplicationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ReplaceWarehouseUseCaseTest {

  private WarehouseStore warehouseStore;
  private ArchiveWarehouseOperation archiveWarehouseOperation;
  private ReplaceWarehouseUseCase replaceWarehouseUseCase;

  @BeforeEach
  void setUp() {
    warehouseStore = mock(WarehouseStore.class);
    archiveWarehouseOperation = mock(ArchiveWarehouseOperation.class);
    replaceWarehouseUseCase = new ReplaceWarehouseUseCase(warehouseStore, archiveWarehouseOperation);
  }

  @Test
  void testReplaceSuccessfully() {
    Warehouse oldW = new Warehouse();
    oldW.businessUnitCode = "BU1";
    oldW.stock = 10;
    
    Warehouse newW = new Warehouse();
    newW.businessUnitCode = "BU1";
    newW.capacity = 20;
    newW.stock = 10;

    when(warehouseStore.findByBusinessUnitCode("BU1")).thenReturn(oldW);

    replaceWarehouseUseCase.replace(newW);

    verify(archiveWarehouseOperation, times(1)).archive(oldW);
    verify(warehouseStore, times(1)).create(newW);
  }

  @Test
  void testReplaceFailsIfOldNotFound() {
    Warehouse newW = new Warehouse();
    newW.businessUnitCode = "BU1";
    
    when(warehouseStore.findByBusinessUnitCode("BU1")).thenReturn(null);

    assertThrows(WebApplicationException.class, () -> replaceWarehouseUseCase.replace(newW));
  }
}
