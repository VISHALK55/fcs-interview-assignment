package com.fulfilment.application.monolith.fulfillment.adapters.restapi;

import com.fulfilment.application.monolith.fulfillment.domain.models.FulfillmentAssociation;
import com.fulfilment.application.monolith.fulfillment.domain.ports.AssociateFulfillmentOperation;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("fulfillment")
@Produces("application/json")
@Consumes("application/json")
public class FulfillmentResourceImpl {

  @Inject
  AssociateFulfillmentOperation associateFulfillmentOperation;

  public static class AssociationRequest {
    public Long storeId;
    public Long productId;
    public String warehouseBusinessUnitCode;
  }

  @POST
  public Response associate(AssociationRequest request) {
    FulfillmentAssociation spw = associateFulfillmentOperation.associate(request.storeId, request.productId, request.warehouseBusinessUnitCode);
    return Response.ok(spw).status(201).build();
  }
}
