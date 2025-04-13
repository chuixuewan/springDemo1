package com.tzj.springdemo1;

import com.tzj.springdemo1.OkHttpDemo.EnhancedHttpClient;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class EnhancedOkHttpTest {

    @Autowired
    private EnhancedHttpClient httpClient;
    
    @Test
    public void testSyncRequest() {
        System.out.println("=== Testing synchronous request with enhanced client ===");
        try {
            Request request = httpClient.getRequestBuilder("https://jsonplaceholder.typicode.com/todos/1")
                    .build();
                    
            Response response = httpClient.sendSyncRequest(request);
            
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println("Response body: " + responseBody);
            } else {
                System.out.println("Request failed: " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    @Test
    public void testAsyncRequest() throws InterruptedException {
        System.out.println("=== Testing asynchronous request with enhanced client ===");
        
        // CountDownLatch to wait for async operation to complete
        final CountDownLatch latch = new CountDownLatch(1);
        
        Request request = httpClient.getRequestBuilder("https://jsonplaceholder.typicode.com/users/1")
                .build();
                
        httpClient.sendAsyncRequest(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Async request failed: " + e.getMessage());
                latch.countDown();
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        System.out.println("Async response body: " + responseBody);
                    } else {
                        System.out.println("Async request failed: " + response.code());
                    }
                } finally {
                    response.close();
                    latch.countDown();
                }
            }
        });
        
        // Wait for the async call to complete (max 5 seconds)
        latch.await(5, TimeUnit.SECONDS);
        System.out.println("Async test completed");
    }
    
    @Test
    public void testPostRequest() {
        System.out.println("=== Testing POST request with enhanced client ===");
        try {
            String jsonBody = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
            RequestBody body = httpClient.createJsonBody(jsonBody);
            
            Request request = httpClient.getRequestBuilder("https://jsonplaceholder.typicode.com/posts")
                    .post(body)
                    .build();
                    
            Response response = httpClient.sendSyncRequest(request);
            
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println("Response body: " + responseBody);
            } else {
                System.out.println("Request failed: " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    @Test
    public void testCustomHeaders() {
        System.out.println("=== Testing request with custom headers ===");
        try {
            Request request = httpClient.getRequestBuilder("https://jsonplaceholder.typicode.com/posts/1")
                    .addHeader("Custom-Header", "custom-value")
                    .addHeader("X-Request-ID", "123456")
                    .build();
                    
            Response response = httpClient.sendSyncRequest(request);
            
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println("Response body: " + responseBody);
            } else {
                System.out.println("Request failed: " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
} 