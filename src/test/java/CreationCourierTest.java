import pojo.CreateCourierJson;
import pojo.LoginCourierJson;
import org.junit.After;
import org.junit.Test;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class CreationCourierTest {
    CreateCourierJson jsonForCreating = new CreateCourierJson(ApiConstants.COURIER_LOGIN, ApiConstants.COURIER_PASSWORD, ApiConstants.COURIER_FIRSTNAME);
    LoginCourierJson jsonForLogin = new LoginCourierJson(jsonForCreating.getLogin(), jsonForCreating.getPassword());
    int id;

    @Test
    public void createCourier() {

        CourierApi.sendPostRequest(jsonForCreating, ApiConstants.URL_FOR_CREATING)
                .statusCode(SC_CREATED)
                .assertThat().body("ok", equalTo(true));

        id = CourierApi.sendPostRequest(jsonForLogin, ApiConstants.URL_FOR_LOGIN)
                .statusCode(SC_OK)
                .assertThat().body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    public void tryToCreateTwoSameCourier() {
        CourierApi.sendPostRequest(jsonForCreating, ApiConstants.URL_FOR_CREATING)
                .statusCode(SC_CREATED)
                .assertThat().body("ok", equalTo(true));

        id = CourierApi.sendPostRequest(jsonForLogin, ApiConstants.URL_FOR_LOGIN)
                .statusCode(SC_OK)
                .assertThat().body("id", notNullValue())
                .extract().path("id");

        CourierApi.sendPostRequest(jsonForCreating, ApiConstants.URL_FOR_CREATING)
                .statusCode(SC_CONFLICT)
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void canNotCreateCourierWithoutLogin() {
        LoginCourierJson loginCourier = new LoginCourierJson("", jsonForCreating.getLogin());

        CourierApi.sendPostRequest(loginCourier, ApiConstants.URL_FOR_CREATING)
                .statusCode(SC_BAD_REQUEST)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void canNotCreatingCourierWithoutPassword() {
        LoginCourierJson loginCourier = new LoginCourierJson(jsonForCreating.getLogin(), "");

        CourierApi.sendPostRequest(loginCourier, ApiConstants.URL_FOR_CREATING)
                .statusCode(SC_BAD_REQUEST)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        CourierApi.deleteCourier(ApiConstants.URL_FOR_CREATING, id);
    }
}


