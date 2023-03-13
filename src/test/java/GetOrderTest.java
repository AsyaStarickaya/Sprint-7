import Pojo.GetOrdersFullJson;
import Pojo.Orders;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class GetOrderTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void getOrder() {
        GetOrdersFullJson getOrdersJson = given()
                .header("Content-type", "application/json")
                .log().all()
                .get("/api/v1/orders")
                .as(GetOrdersFullJson.class);

        Assert.assertNotNull(getOrdersJson.getOrders());
    }
}
