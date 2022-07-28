package example.client;

import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;


public class ConnectionFactory implements HttpURLConnectionFactory {

    Proxy proxy;

    public void initializeProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection(proxy);
    }
}