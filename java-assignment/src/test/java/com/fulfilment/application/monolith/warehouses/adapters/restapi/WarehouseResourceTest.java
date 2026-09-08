package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import com.warehouse.api.beans.Warehouse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class WarehouseResourceTest {

    @Test
    public void testListWarehouses() {
        given()
          .when().get("/warehouse")
          .then()
             .statusCode(200);
    }
    
    @Test
    public void testCreateWarehouse() {
        Warehouse w = new Warehouse();
        w.setBusinessUnitCode("TEST-WH-1");
        w.setLocation("MWH.012");
        w.setCapacity(10);
        w.setStock(10);
        
        given()
          .contentType(ContentType.JSON)
          .body(w)
          .when().post("/warehouse")
          .then()
             .statusCode(201);
    }
}
