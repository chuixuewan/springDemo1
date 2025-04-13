package com.tzj.springdemo1.OkHttpDemo;

import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A service class that demonstrates various OkHttp operations
 */
@Service
public class HttpService {
    
    private final OkHttpClient client;
    
    public HttpService() {
        // Creating a client with custom configuration
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }
    
    /**
     * Performs a simple GET request
     * 
     * @param url The URL to send the request to
     * @return The response body as a string
     * @throws IOException If the request fails
     */
    public String performGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
                
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            return response.body() != null ? response.body().string() : "";
        }
    }
    
    /**
     * Performs a POST request with a JSON body
     * 
     * @param url The URL to send the request to
     * @param jsonBody The JSON body to send
     * @return The response body as a string
     * @throws IOException If the request fails
     */
    public String performPostJson(String url, String jsonBody) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(jsonBody, JSON);
        
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
                
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            return response.body() != null ? response.body().string() : "";
        }
    }
    
    /**
     * Performs a POST request with form data
     * 
     * @param url The URL to send the request to
     * @param formParams The form parameters
     * @return The response body as a string
     * @throws IOException If the request fails
     */
    public String performPostForm(String url, Map<String, String> formParams) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        
        // Add all form parameters
        for (Map.Entry<String, String> entry : formParams.entrySet()) {
            formBuilder.add(entry.getKey(), entry.getValue());
        }
        
        RequestBody formBody = formBuilder.build();
        
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
                
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            return response.body() != null ? response.body().string() : "";
        }
    }
    
    /**
     * Sends a request with custom headers
     * 
     * @param url The URL to send the request to
     * @param headers The headers to include
     * @return The response body as a string
     * @throws IOException If the request fails
     */
    public String performRequestWithHeaders(String url, Map<String, String> headers) throws IOException {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        
        // Add all headers
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        
        Request request = requestBuilder.build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            return response.body() != null ? response.body().string() : "";
        }
    }
} 