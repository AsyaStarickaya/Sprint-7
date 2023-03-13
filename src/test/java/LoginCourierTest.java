import Pojo.CreateCourierJson;
import Pojo.LoginCourierJson;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    CreateCourierJson createCourierJson = new CreateCourierJson("nkjame11313", "12345", "FirstName1313");
    LoginCourierJson loginCourierJson = new LoginCourierJson(createCourierJson.getLogin(), createCourierJson.getPassword());
    int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";

    }

    @Test
    public void loginCourier() {
        given()
                .header("Content-type", "application/json")
                .body(createCourierJson)
                .log().all()
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);

        id = given()
                .header("Content-type", "application/json")
                .body(loginCourierJson)
                .log().all()
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat().body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    public void loginWithoutLogin() {
        LoginCourierJson loginCourierJson = new LoginCourierJson("", createCourierJson.getPassword());
        given()
                .header("Content-type", "application/json")
                .body(loginCourierJson)
                .log().all()
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    public void loginWithoutPassword() {
        LoginCourierJson loginCourierJson = new LoginCourierJson(createCourierJson.getLogin(), "");
        given()
                .header("Content-type", "application/json")
                .body(loginCourierJson)
                .log().all()
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void tryToLoginWithNonExistedAccount() {
        LoginCourierJson loginCourierJson = new LoginCourierJson("fghjk1238bn", "12345");
        given()
                .header("Content-type", "application/json")
                .body(loginCourierJson)
                .log().all()
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        given()
                .delete("/api/v1/courier/" + id);
    }

}
