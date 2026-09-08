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
        w.setLocation("VETSBY-001");
        w.setCapacity(10);
        w.setStock(10);
        
        given()
          .contentType(ContentType.JSON)
          .body(w)
          .when().post("/warehouse")
          .then()
             .statusCode(200);
    }

    @Test
    public void testGetWarehouse() {
        given()
          .when().get("/warehouse/MWH.001")
          .then()
             .statusCode(200);
    }

    @Test
    public void testGetWarehouseNotFound() {
        given()
          .when().get("/warehouse/NON-EXISTENT")
          .then()
             .statusCode(404);
    }

    @Test
    public void testUpdateWarehouse() {
        Warehouse w = new Warehouse();
        w.setBusinessUnitCode("MWH.012");
        w.setLocation("AMSTERDAM-001");
        w.setCapacity(40);
        w.setStock(5);

        given()
          .contentType(ContentType.JSON)
          .body(w)
          .when().put("/warehouse/MWH.012")
          .then()
             .statusCode(200);
    }

    @Test
    public void testDeleteWarehouse() {
        given()
          .when().delete("/warehouse/MWH.023")
          .then()
             .statusCode(204);
    }
}
