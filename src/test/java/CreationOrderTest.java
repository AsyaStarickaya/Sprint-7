import pojo.CreateOrderJson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.*;
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


    @Test
    public void createOrder() throws Exception {
        CreateOrderJson orderJson = new CreateOrderJson(color);
        OrderApi.sendPostRequest(orderJson, ApiConstants.URL_FOR_ORDER)
                .statusCode(SC_CREATED)
                .assertThat().body("track", notNullValue());
    }
}
