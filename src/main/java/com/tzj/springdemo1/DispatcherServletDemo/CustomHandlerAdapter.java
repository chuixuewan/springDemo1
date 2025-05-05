package com.tzj.springdemo1.DispatcherServletDemo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;

/**
 * A custom HandlerAdapter implementation to demonstrate how HandlerAdapter works
 * This adapter knows how to handle our CustomHandler objects
 */
@Component
public class CustomHandlerAdapter implements HandlerAdapter, Ordered {

    @Override
    public boolean supports(Object handler) {
        // This adapter only supports CustomHandler objects
        return handler instanceof CustomHandler;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Cast to our custom handler type
        CustomHandler customHandler = (CustomHandler) handler;
        
        // Set response content type
        response.setContentType("text/plain;charset=UTF-8");
        
        // Write the response directly to the output
        PrintWriter writer = response.getWriter();
        writer.write(customHandler.getResponse());
        writer.flush();
        
        // Return null because we've handled the response completely
        return null;
    }

    @Override
    public long getLastModified(HttpServletRequest request, Object handler) {
        // We don't support caching based on modification time
        return -1;
    }
    
    @Override
    public int getOrder() {
        // Higher priority than other adapters
        return -1;
    }
} 