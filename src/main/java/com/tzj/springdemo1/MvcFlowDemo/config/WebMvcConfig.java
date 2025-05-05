package com.tzj.springdemo1.MvcFlowDemo.config;

import com.tzj.springdemo1.MvcFlowDemo.interceptor.CustomInterceptor;
import com.tzj.springdemo1.MvcFlowDemo.interceptor.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to register interceptors with the Spring MVC framework.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;
    private final CustomInterceptor customInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Registering Spring MVC interceptors");
        
        // Add the logging interceptor to all /flow/** paths
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/flow/**")
                .order(1); // Lower numbers have higher priority
        
        // Add the custom interceptor to all /flow/** paths
        registry.addInterceptor(customInterceptor)
                .addPathPatterns("/flow/**")
                .order(2);
        
        log.info("Interceptors registered successfully");
    }
} 