package com.tzj.springdemo1.MvcFlowDemo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * A standard interceptor that logs information about handler execution.
 */
@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("=== [LoggingInterceptor] preHandle ===");
        
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            log.info("Controller: {}", handlerMethod.getBeanType().getSimpleName());
            log.info("Method: {}", handlerMethod.getMethod().getName());
        } else {
            log.info("Handler: {}", handler.getClass().getSimpleName());
        }
        
        log.info("===============================");
        
        // Store time for performance measurement
        request.setAttribute("startTime", System.currentTimeMillis());
        
        // Return true to continue with the handler execution
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("=== [LoggingInterceptor] postHandle ===");
        
        if (modelAndView != null) {
            log.info("View Name: {}", modelAndView.getViewName());
            log.info("Model: {}", modelAndView.getModel());
        } else {
            log.info("No ModelAndView returned");
        }
        
        log.info("================================");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        
        log.info("=== [LoggingInterceptor] afterCompletion ===");
        log.info("Request Processing Time: {} ms", endTime - startTime);
        
        if (ex != null) {
            log.error("Exception occurred: {}", ex.getMessage());
        }
        
        log.info("=======================================");
    }
} 