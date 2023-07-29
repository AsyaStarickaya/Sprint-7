import pojo.GetOrdersFullJson;

import static io.restassured.RestAssured.given;

public class OrderApi extends BaseApi{
    public static GetOrdersFullJson getOrder (String URL){
        return given()
                .header("Content-type", "application/json")
                .log().all()
                .get(URL)
                .as(GetOrdersFullJson.class);
    }
}
