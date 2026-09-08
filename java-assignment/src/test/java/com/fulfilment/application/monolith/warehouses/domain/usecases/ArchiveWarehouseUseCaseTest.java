package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ArchiveWarehouseUseCaseTest {

  private WarehouseStore warehouseStore;
  private ArchiveWarehouseUseCase archiveWarehouseUseCase;

  @BeforeEach
  void setUp() {
    warehouseStore = mock(WarehouseStore.class);
    archiveWarehouseUseCase = new ArchiveWarehouseUseCase(warehouseStore);
  }

  @Test
  void testArchiveSuccessfully() {
    Warehouse w = new Warehouse();
    w.businessUnitCode = "BU1";
    
    archiveWarehouseUseCase.archive(w);

    assertNotNull(w.archivedAt);
    verify(warehouseStore, times(1)).update(w);
  }
}
