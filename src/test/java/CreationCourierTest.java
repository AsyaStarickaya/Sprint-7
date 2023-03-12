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
    CreateCourierJson jsonForCreating = new CreateCourierJson("puf35", "1235010", "Qwerty-Ui111o");
    LoginCourierJson jsonForLogin = new LoginCourierJson(jsonForCreating.getLogin(), jsonForCreating.getPassword());
    int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void tearDown() {
        given()
                .delete("/api/v1/courier/" + id);
    }

    @Test
    public void createCourier() {
        given()
                .header("Content-type", "application/json")
                .body(jsonForCreating)
                .log().all()
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));

        id = given()
                .header("Content-type", "application/json")
                .body(jsonForLogin)
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
    public void tryToCreateTwoSameCourier() {
        given()
                .header("Content-type", "application/json")
                .body(jsonForCreating)
                .log().all()
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .statusCode(201);

        id = given()
                .header("Content-type", "application/json")
                .body(jsonForLogin)
                .log().all()
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat().body("id", notNullValue())
                .extract().path("id");

        given()
                .header("Content-type", "application/json")
                .body(jsonForCreating)
                .log().all()
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .statusCode(409)
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void canNotCreateCourierWithoutLogin() {
        LoginCourierJson loginCourier = new LoginCourierJson("", jsonForCreating.getLogin());

        given()
                .header("Content-type", "application/json")
                .body(loginCourier)
                .log().all()
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void canNotCreatingCourierWithoutPassword() {
        LoginCourierJson loginCourier = new LoginCourierJson(jsonForCreating.getLogin(), "");

        given()
                .header("Content-type", "application/json")
                .body(loginCourier)
                .log().all()
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}

//продолжение следует...
