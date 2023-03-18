import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import pojo.GetOrdersFullJson;

import static io.restassured.RestAssured.given;

public class CourierApi extends BaseApi{
    public static Response deleteCourier(String URL, int id) {
        return given()
                .delete(URL  + "/" + id);
    }
}
