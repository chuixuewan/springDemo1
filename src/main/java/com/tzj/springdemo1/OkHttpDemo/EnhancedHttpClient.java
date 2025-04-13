package com.tzj.springdemo1.OkHttpDemo;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Enhanced OkHttp client that demonstrates more advanced features like interceptors
 */
@Component
public class EnhancedHttpClient {

    private final OkHttpClient client;
    
    public EnhancedHttpClient() {
        // Create a logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        // Create a custom interceptor for adding headers to all requests
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                
                // Add common headers to all requests
                Request newRequest = originalRequest.newBuilder()
                        .header("User-Agent", "OkHttp Demo Client")
                        .header("Accept", "application/json")
                        .build();
                
                return chain.proceed(newRequest);
            }
        };
        
        // Create a custom interceptor for handling responses
        Interceptor responseInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long startTime = System.nanoTime();
                
                Response response = chain.proceed(request);
                
                long endTime = System.nanoTime();
                long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
                
                System.out.println("[Interceptor] Request to " + request.url() + " took " + duration + "ms");
                
                // You could modify the response here if needed
                return response;
            }
        };
        
        // Configure the OkHttpClient with the interceptors
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)         // Add headers to all requests
                .addInterceptor(responseInterceptor)       // Track timing and response handling
                .addNetworkInterceptor(loggingInterceptor) // Log network activity
                .build();
    }
    
    /**
     * Send an HTTP request and handle the response with a callback
     * Demonstrates asynchronous request handling
     * 
     * @param request The request to send
     * @param responseCallback The callback to handle the response
     */
    public void sendAsyncRequest(Request request, Callback responseCallback) {
        client.newCall(request).enqueue(responseCallback);
    }
    
    /**
     * Send an HTTP request synchronously
     * 
     * @param request The request to send
     * @return The response
     * @throws IOException If the request fails
     */
    public Response sendSyncRequest(Request request) throws IOException {
        return client.newCall(request).execute();
    }
    
    /**
     * Helper method to create a simple GET request builder
     * 
     * @param url The URL for the request
     * @return A new Request.Builder with the URL set
     */
    public Request.Builder getRequestBuilder(String url) {
        return new Request.Builder().url(url);
    }
    
    /**
     * Helper method to create a JSON request body
     * 
     * @param jsonBody The JSON string
     * @return A new RequestBody
     */
    public RequestBody createJsonBody(String jsonBody) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        return RequestBody.create(jsonBody, JSON);
    }
} 