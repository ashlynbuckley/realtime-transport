package com.transport.silversurfer.client;

import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;

// Handles HTTP requests to NTA's GTFS-Realtime API
// https://developer.nationaltransport.ie/api-details#api=gtfsr&operation=gtfsr-v2
public class ApiClient {
    String apiUrl = "https://api.silversurfer.com";

    public void createHttpClient() {
        // Putting it in a try-with-resources to ensure the resource is closed
        // This CloseableHttpClient will be the reusable client I use to send requests
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            // Builder for GET reqs
            ClassicHttpRequest httpGet = ClassicRequestBuilder.get("http://httpbin.org/get")
                    .build();

            // using a lambda means the response will be closed after the lambda finishes
            httpclient.execute(httpGet, response -> {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                            // Convert the entity to a string (JSON text)
                            String json = EntityUtils.toString(entity);
                            System.out.println("Response JSON:");
                            System.out.println(json);
                }
                // closes entity
                EntityUtils.consume(entity);
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}