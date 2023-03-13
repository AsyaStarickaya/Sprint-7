import Pojo.CreateOrderJson;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreationOrderTest {
    private final List<String> color;


    public CreationOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                new List[]{List.of("BLACK")},
                new List[]{List.of("GREY")},
                new List[]{List.of("BLACK", "GREY")},
                new List[]{List.of("")}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void createOrder() throws Exception {
        CreateOrderJson orderJson = new CreateOrderJson(color);
        given()
                .header("Content-type", "application/json")
                .body(orderJson)
                .log().all()
                .when()
                .post("/api/v1/orders")
                .then()
                .log().all()
                .statusCode(201)
                .assertThat().body("track", notNullValue());
    }
}
