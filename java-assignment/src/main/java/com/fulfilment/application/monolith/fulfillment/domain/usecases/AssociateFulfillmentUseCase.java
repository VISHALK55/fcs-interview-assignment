package com.fulfilment.application.monolith.fulfillment.domain.usecases;

import com.fulfilment.application.monolith.fulfillment.domain.models.FulfillmentAssociation;
import com.fulfilment.application.monolith.fulfillment.domain.ports.AssociateFulfillmentOperation;
import com.fulfilment.application.monolith.fulfillment.domain.ports.FulfillmentStore;
import com.fulfilment.application.monolith.fulfillment.domain.validators.FulfillmentValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AssociateFulfillmentUseCase implements AssociateFulfillmentOperation {

  private final FulfillmentStore fulfillmentStore;
  private final FulfillmentValidator validator;

  public AssociateFulfillmentUseCase(FulfillmentStore fulfillmentStore, FulfillmentValidator validator) {
    this.fulfillmentStore = fulfillmentStore;
    this.validator = validator;
  }

  @Override
  @Transactional
  public FulfillmentAssociation associate(Long storeId, Long productId, String warehouseBuCode) {
    validator.validateAssociation(storeId, productId, warehouseBuCode);

    FulfillmentAssociation association = new FulfillmentAssociation(storeId, productId, warehouseBuCode);
    fulfillmentStore.create(association);
    return association;
  }
}
