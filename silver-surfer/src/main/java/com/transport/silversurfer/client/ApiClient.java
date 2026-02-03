package com.transport.silversurfer.client;

import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;

// Handles HTTP requests to NTA's GTFS-Realtime API
// https://developer.nationaltransport.ie/api-details#api=gtfsr&operation=gtfsr-v2
public class ApiClient {

    public void createHttpClient() {
        //putting it in a try-with-resources to ensure the resource is closed
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            ClassicHttpRequest httpGet = ClassicRequestBuilder.get("http://httpbin.org/get")
                    .build();

            httpclient.execute(httpGet, response -> {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                final HttpEntity entity1 = response.getEntity();
                EntityUtils.consume(entity1);
                return null;
            });

//            ClassicHttpRequest httpPost = ClassicRequestBuilder.post("http://httpbin.org/post")
//                    .setEntity(new UrlEncodedFormEntity(Arrays.asList(
//                            new BasicNameValuePair("username", "vip"),
//                            new BasicNameValuePair("password", "secret"))))
//                    .build();
//            httpclient.execute(httpPost, response -> {
//                System.out.println(response.getCode() + " " + response.getReasonPhrase());
//                final HttpEntity entity2 = response.getEntity();
//                EntityUtils.consume(entity2);
//                return null;
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}