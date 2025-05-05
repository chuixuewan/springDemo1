package com.tzj.springdemo1;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * This test explores the structure of HandlerMethod, which is a key class in Spring MVC
 * HandlerMethod represents a controller method that handles a particular request
 */
@Slf4j
@SpringBootTest
public class HandlerMethodDemoTest {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Test
    public void exploreHandlerMethodStructure() throws Exception {
        // 1. Get all handler methods registered with the request mapping handler
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        
        log.info("Total number of handler methods: {}", handlerMethods.size());
        
        // 2. Loop through and explore each handler method
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            
            // Get the request patterns
            Set<String> patterns = mappingInfo.getPatternValues();
            
            // Get information about the method
            String controllerName = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            
            log.info("\n--- Handler Method Information ---");
            log.info("URL Patterns: {}", patterns);
            log.info("Controller: {}", controllerName);
            log.info("Method: {}", methodName);
            
            // 3. Explore method parameters
            MethodParameter[] parameters = handlerMethod.getMethodParameters();
            log.info("Parameters count: {}", parameters.length);
            
            for (MethodParameter param : parameters) {
                log.info("  Parameter: {} ({})", param.getParameterName(), param.getParameterType().getSimpleName());
                
                // Get annotations on the parameter
                log.info("  Annotations: {}", (Object[]) param.getParameterAnnotations());
            }
            
            // 4. Explore method return type
            MethodParameter returnType = handlerMethod.getReturnType();
            log.info("Return type: {}", returnType.getParameterType().getSimpleName());
            
            // 5. Examine the raw Method object
            Method method = handlerMethod.getMethod();
            log.info("Method signature: {}", method);
            
            // 6. Check controller-level annotations
            RequestMapping controllerMapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
            if (controllerMapping != null) {
                log.info("Controller base path: {}", String.join(", ", controllerMapping.value()));
            }
            
            log.info("-----------------------------\n");
        }
    }
} 