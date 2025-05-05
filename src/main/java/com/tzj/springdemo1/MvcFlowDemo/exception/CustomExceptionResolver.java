package com.tzj.springdemo1.MvcFlowDemo.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tzj.springdemo1.MvcFlowDemo.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Custom exception resolver that handles DemoException instances.
 * This demonstrates how to create a custom HandlerExceptionResolver.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomExceptionResolver implements HandlerExceptionResolver, Ordered {

    private final ObjectMapper objectMapper;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, 
                                        Object handler, Exception ex) {
        log.info("=== [CustomExceptionResolver] Handling exception ===");
        
        // Only handle DemoException instances
        if (!(ex instanceof DemoException)) {
            log.info("Not a DemoException, skipping: {}", ex.getClass().getSimpleName());
            return null; // Let other resolvers handle it
        }
        
        DemoException demoEx = (DemoException) ex;
        log.info("Handling DemoException with code: {}", demoEx.getErrorCode());
        
        try {
            // Set appropriate status code
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            
            // Create error response
            ErrorResponse errorResponse = ErrorResponse.of(
                    demoEx.getMessage(),
                    demoEx.getErrorCode(),
                    request.getRequestURI(),
                    HttpStatus.BAD_REQUEST.value()
            );
            
            // Write the error response to the output
            objectMapper.writeValue(response.getWriter(), errorResponse);
            
            log.info("Custom error response written to output");
            log.info("======================================");
            
            // Return empty ModelAndView to indicate exception is handled
            return new ModelAndView();
            
        } catch (IOException e) {
            log.error("Failed to write error response", e);
            return null;
        }
    }

    @Override
    public int getOrder() {
        // Higher priority than DefaultHandlerExceptionResolver
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
} 