package example.scenarios;

import example.BaseTest;
import example.client.RequestResponse;
import example.datausa.DataService;
import example.utils.Context;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import example.utils.CustomAssertions;

import static example.utils.CustomAssertions.assertEquals;
import static example.utils.CustomAssertions.assertTrue;

@Tag("data")
@Tag("regression")
@DisplayName("Data")
public class DataTest extends BaseTest {

    DataService dataService = new DataService(Context.getProperty("dataservice.url"));
    RequestResponse requestResponse;

    @Test
    @DisplayName("Get data")
    @Description("Some scenario description.")
    void getDataTest() {

        When("request for data", () -> {
            requestResponse = dataService.getExampleData();
        });

        Then("the response is correct", () -> {
            CustomAssertions.assertAll("Response",
                    () -> assertEquals(200, requestResponse.getResponseStatus(), "Response status"),
                    () -> assertTrue(requestResponse.getResponseHeaders().getFirst("Content-Type").contains("application/json"), "Correct response content type")
            );
        });
    }

}
