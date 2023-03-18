import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class BaseApi {
    public static ValidatableResponse sendPostRequest(Object json, String URL) {
        RestAssured.baseURI = ApiConstants.BASE_URI;

        return given()
                .header("Content-type", "application/json")
                .body(json)
                .log().all()
                .when()
                .post(URL)
                .then()
                .log().all();
    }
}
