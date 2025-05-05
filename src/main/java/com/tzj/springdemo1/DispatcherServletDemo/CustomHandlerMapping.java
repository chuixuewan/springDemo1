package com.tzj.springdemo1.DispatcherServletDemo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * A custom HandlerMapping implementation to demonstrate how HandlerMapping works
 * This maps URLs directly to handler objects instead of controller methods
 */
@Component
public class CustomHandlerMapping implements HandlerMapping, Ordered {

    // A simple map to store URL to handler mappings
    private final Map<String, Object> urlHandlers = new HashMap<>();
    
    public CustomHandlerMapping() {
        // Register a custom handler for a specific URL
        urlHandlers.put("/demo/custom", new CustomHandler("This is handled by the CustomHandlerMapping!"));
    }
    
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        String path = request.getRequestURI();
        
        // Check if we have a handler for the requested path
        if (urlHandlers.containsKey(path)) {
            return new HandlerExecutionChain(urlHandlers.get(path));
        }
        
        // Return null to indicate this mapping cannot handle the request
        return null;
    }
    
    @Override
    public int getOrder() {
        // Higher priority than RequestMappingHandlerMapping (which has order 0)
        // Lower number means higher priority
        return -1;
    }
} 