import Pojo.CreateCourierJson;
import Pojo.LoginCourierJson;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreationCourierTest {
    CreateCourierJson jsonForCreating = new CreateCourierJson("AlphaXP", "12345010", "Qwerty-Ui11o");
    LoginCourierJson jsonForLogin = new LoginCourierJson(jsonForCreating.getLogin(), jsonForCreating.getPassword());
    int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void createCourier() {

        given()
                .header("Content-type", "application/json")
                .body(jsonForCreating)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);

        id = given()
                .header("Content-type", "application/json")
                .body(jsonForLogin)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200)
                .assertThat().body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    public void tryToCreateTwoSameCourier() {
        given()
                .header("Content-type", "application/json")
                .body(jsonForCreating)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);

        given()
                .header("Content-type", "application/json")
                .body(jsonForCreating)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .assertThat().body("massage", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void tearDown() {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + id);
    }
}

//продолжение следует...
