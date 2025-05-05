package com.tzj.springdemo1.MvcFlowDemo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * A custom interceptor that demonstrates modifying the request and response.
 */
@Slf4j
@Component
@Order(2) // Will execute after LoggingInterceptor
public class CustomInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("=== [CustomInterceptor] preHandle ===");
        
        // Add custom attribute to demonstrate request modification
        request.setAttribute("customInterceptorTimestamp", System.currentTimeMillis());
        log.info("Added custom timestamp attribute from interceptor");
        
        // Demonstrate examining request information
        String userAgent = request.getHeader("User-Agent");
        log.info("User-Agent: {}", userAgent);
        
        // Get custom attribute added by filter to show the sequence
        Long filterTimestamp = (Long) request.getAttribute("customFilterTimestamp");
        if (filterTimestamp != null) {
            log.info("Filter processing occurred {} ms before interceptor", 
                    System.currentTimeMillis() - filterTimestamp);
        }
        
        log.info("===================================");
        
        // Return true to continue with the handler execution
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("=== [CustomInterceptor] postHandle ===");
        
        // Modify ModelAndView if it exists
        if (modelAndView != null) {
            // Add a custom attribute to the model
            modelAndView.addObject("interceptorMessage", "Added by CustomInterceptor");
            log.info("Added custom attribute to ModelAndView");
        }
        
        // Add a custom header to the response
        response.setHeader("X-Custom-Interceptor", "Processed");
        log.info("Added custom header to response");
        
        log.info("====================================");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("=== [CustomInterceptor] afterCompletion ===");
        
        // Demonstrate timing end-to-end processing
        Long timestamp = (Long) request.getAttribute("customInterceptorTimestamp");
        long processingTime = System.currentTimeMillis() - timestamp;
        
        log.info("Total processing time from interceptor: {} ms", processingTime);
        
        // Log any exception that might have occurred
        if (ex != null) {
            log.error("Exception details: ", ex);
        }
        
        log.info("=========================================");
    }
} 