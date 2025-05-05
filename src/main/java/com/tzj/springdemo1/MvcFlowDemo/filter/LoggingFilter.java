package com.tzj.springdemo1.MvcFlowDemo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A standard Spring filter that logs request information.
 */
@Slf4j
@Component
@Order(1) // Lower numbers have higher priority
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        
        log.info("=== [LoggingFilter] PRE PROCESSING ===");
        log.info("Request URI: {}", requestURI);
        log.info("Request Method: {}", method);
        log.info("=====================================");
        
        // Record the start time
        long startTime = System.currentTimeMillis();
        
        try {
            // Continue with the filter chain
            filterChain.doFilter(request, response);
        } finally {
            // Calculate execution time
            long duration = System.currentTimeMillis() - startTime;
            
            log.info("=== [LoggingFilter] POST PROCESSING ===");
            log.info("Response Status: {}", response.getStatus());
            log.info("Request Duration: {} ms", duration);
            log.info("======================================");
        }
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Only filter requests to our demo paths
        String path = request.getRequestURI();
        return !path.startsWith("/flow");
    }
} 