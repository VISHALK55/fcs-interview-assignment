package com.fulfilment.application.monolith.fulfillment.domain.models;

public class FulfillmentAssociation {
  public Long storeId;
  public Long productId;
  public String warehouseBusinessUnitCode;

  public FulfillmentAssociation() {}

  public FulfillmentAssociation(Long storeId, Long productId, String warehouseBusinessUnitCode) {
    this.storeId = storeId;
    this.productId = productId;
    this.warehouseBusinessUnitCode = warehouseBusinessUnitCode;
  }
}
