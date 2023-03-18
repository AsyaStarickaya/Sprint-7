import pojo.CreateCourierJson;
import pojo.LoginCourierJson;
import org.junit.After;
import org.junit.Test;


import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    CreateCourierJson jsonForCreating = new CreateCourierJson(ApiConstants.COURIER_LOGIN, ApiConstants.COURIER_PASSWORD, ApiConstants.COURIER_FIRSTNAME);
    LoginCourierJson jsonForLogin = new LoginCourierJson(jsonForCreating.getLogin(), jsonForCreating.getPassword());
    int id;

    @Test
    public void loginCourier() {
        CourierApi.sendPostRequest(jsonForCreating, ApiConstants.URL_FOR_CREATING)
                .statusCode(SC_CREATED);

        id = CourierApi.sendPostRequest(jsonForLogin, ApiConstants.URL_FOR_LOGIN)
                .statusCode(SC_OK)
                .assertThat().body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    public void loginWithoutLogin() {
        LoginCourierJson loginCourierJson = new LoginCourierJson("", jsonForCreating.getPassword());
        CourierApi.sendPostRequest(loginCourierJson, ApiConstants.URL_FOR_LOGIN)
                .statusCode(SC_BAD_REQUEST)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    public void loginWithoutPassword() {
        LoginCourierJson loginCourierJson = new LoginCourierJson(jsonForCreating.getLogin(), "");
        CourierApi.sendPostRequest(loginCourierJson, ApiConstants.URL_FOR_LOGIN)
                .statusCode(SC_BAD_REQUEST)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void tryToLoginWithNonExistedAccount() {
        LoginCourierJson loginCourierJson = new LoginCourierJson(ApiConstants.COURIER_LOGIN, ApiConstants.COURIER_PASSWORD);
        CourierApi.sendPostRequest(loginCourierJson, ApiConstants.URL_FOR_LOGIN)
                .statusCode(SC_NOT_FOUND)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        CourierApi.deleteCourier(ApiConstants.URL_FOR_CREATING, id);
    }
}
