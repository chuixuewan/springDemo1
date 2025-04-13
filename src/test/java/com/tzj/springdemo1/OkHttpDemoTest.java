package com.tzj.springdemo1;

import com.tzj.springdemo1.OkHttpDemo.HttpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class OkHttpDemoTest {

    @Autowired
    private HttpService httpService;
    
    @Test
    public void testGetRequest() {
        System.out.println("=== Testing GET request ===");
        try {
            String response = httpService.performGet("https://jsonplaceholder.typicode.com/posts/1");
            System.out.println("Response: " + response);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    @Test
    public void testPostJsonRequest() {
        System.out.println("=== Testing POST JSON request ===");
        try {
            String jsonBody = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
            String response = httpService.performPostJson("https://jsonplaceholder.typicode.com/posts", jsonBody);
            System.out.println("Response: " + response);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    @Test
    public void testPostFormRequest() {
        System.out.println("=== Testing POST form request ===");
        try {
            Map<String, String> formParams = new HashMap<>();
            formParams.put("title", "foo");
            formParams.put("body", "bar");
            formParams.put("userId", "1");
            
            String response = httpService.performPostForm("https://jsonplaceholder.typicode.com/posts", formParams);
            System.out.println("Response: " + response);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    @Test
    public void testRequestWithHeaders() {
        System.out.println("=== Testing request with headers ===");
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer fake-token");
            headers.put("Accept", "application/json");
            headers.put("Custom-Header", "custom-value");
            
            String response = httpService.performRequestWithHeaders("https://jsonplaceholder.typicode.com/posts/1", headers);
            System.out.println("Response: " + response);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
} 