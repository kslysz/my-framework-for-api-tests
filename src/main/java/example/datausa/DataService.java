package example.datausa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import example.client.RequestResponse;
import example.utils.Log;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.Map;

public class DataService {

    private final String serviceUrl;
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private final Client client;
    private final MultivaluedMap<String, String> headers = new MultivaluedMapImpl();

    public DataService(String serviceUrl) {
        this.serviceUrl = serviceUrl;
        client = Client.create();
        addBaseHeaders();
    }

    private void addBaseHeaders() {
        headers.putSingle("User-Agent", "api-client");
        headers.putSingle("Content-Type", "application/json");
    }

    private void setHeaders(WebResource.Builder builder) {
        for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
            String value = entry.getValue().toString();
            builder = builder.header(entry.getKey(), value.substring(1, value.length() - 1));
        }
    }

    public RequestResponse getExampleData() {
        WebResource webResource = this.client.resource(this.serviceUrl);
        WebResource.Builder builder = webResource.getRequestBuilder();
        setHeaders(builder);

        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setRequestMethod("GET");
        requestResponse.setRequestHeaders(headers);
        requestResponse.setRequestUrl(webResource.getURI().toString());

        long start = System.currentTimeMillis();
        try {
            ClientResponse response = builder.get(ClientResponse.class);
            requestResponse.setResponseStatus(response.getStatus());
            requestResponse.setResponseCorrect(response.getStatus() == 200);
            requestResponse.setResponseHeaders(response.getHeaders());
            requestResponse.setResponseBody(response.getEntity(String.class));
        } catch (Exception e) {
            requestResponse.setResponseCorrect(false);
            requestResponse.setException(e);
        } finally {
            requestResponse.setResponseDuration(System.currentTimeMillis()-start);
            Log.requestResponseStep(requestResponse, "Get data");
        }

        return requestResponse;
    }
}
