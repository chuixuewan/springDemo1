package com.tzj.springdemo1.MvcFlowDemo.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A custom filter implementation demonstrating the Filter interface directly.
 */
@Slf4j
@Component
@Order(2) // Will execute after LoggingFilter
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("=== [CustomFilter] Initialized ===");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        // Only process requests to our demo paths
        if (!httpRequest.getRequestURI().startsWith("/flow")) {
            chain.doFilter(request, response);
            return;
        }
        
        log.info("=== [CustomFilter] PRE PROCESSING ===");
        log.info("Thread Name: {}", Thread.currentThread().getName());
        
        // Add custom request attribute to demonstrate modification
        request.setAttribute("customFilterTimestamp", System.currentTimeMillis());
        log.info("Added custom timestamp attribute to request");
        log.info("=====================================");
        
        try {
            // Continue with the filter chain
            chain.doFilter(request, response);
        } finally {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            
            log.info("=== [CustomFilter] POST PROCESSING ===");
            log.info("Response Content Type: {}", httpResponse.getContentType());
            
            // Add a custom header to demonstrate response modification
            httpResponse.setHeader("X-Custom-Filter", "Processed");
            log.info("Added custom header to response");
            log.info("=====================================");
        }
    }

    @Override
    public void destroy() {
        log.info("=== [CustomFilter] Destroyed ===");
    }
} 