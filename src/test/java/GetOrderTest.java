import pojo.GetOrdersFullJson;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetOrderTest {

    @Test
    public void getOrder() {
        GetOrdersFullJson getOrdersJson = OrderApi.getOrder(ApiConstants.URL_FOR_ORDER);
        Assert.assertNotNull(getOrdersJson.getOrders());
    }
}
