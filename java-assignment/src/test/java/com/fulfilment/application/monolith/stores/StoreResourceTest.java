package com.fulfilment.application.monolith.stores;

import com.fulfilment.application.monolith.stores.Store;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class StoreResourceTest {

    @Test
    public void testListStores() {
        given()
          .when().get("/store")
          .then()
             .statusCode(200);
    }

    @Test
    public void testGetStore() {
        given()
          .when().get("/store/1")
          .then()
             .statusCode(200);
    }

    @Test
    public void testCreateStore() {
        Store s = new Store();
        s.name = "NEW STORE";
        s.quantityProductsInStock = 5;

        given()
          .contentType(ContentType.JSON)
          .body(s)
          .when().post("/store")
          .then()
             .statusCode(201);
    }

    @Test
    public void testUpdateStore() {
        Store s = new Store();
        s.name = "UPDATED STORE";
        s.quantityProductsInStock = 10;

        given()
          .contentType(ContentType.JSON)
          .body(s)
          .when().put("/store/2")
          .then()
             .statusCode(200);
    }

    @Test
    public void testDeleteStore() {
        given()
          .when().delete("/store/3")
          .then()
             .statusCode(204);
    }
}
