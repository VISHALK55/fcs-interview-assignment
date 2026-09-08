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
}
