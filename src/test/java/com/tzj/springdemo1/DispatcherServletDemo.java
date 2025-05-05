package com.tzj.springdemo1;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class DispatcherServletDemo {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testDispatcherServletComponents() throws Exception {
        // 1. Testing the entire flow
        MvcResult result = mockMvc.perform(get("/demo/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from DispatcherServlet Demo!"))
                .andReturn();
        
        log.info("Response: {}", result.getResponse().getContentAsString());
        
        // 2. Examining DispatcherServlet
        DispatcherServlet dispatcherServlet = applicationContext.getBean(DispatcherServlet.class);
        assertNotNull(dispatcherServlet, "DispatcherServlet should be available in the context");
        log.info("DispatcherServlet: {}", dispatcherServlet);
        
        // 3. Examining HandlerMappings
        List<HandlerMapping> handlerMappings = applicationContext.getBeansOfType(HandlerMapping.class).values().stream().toList();
        log.info("Number of HandlerMappings: {}", handlerMappings.size());
        
        handlerMappings.forEach(mapping -> 
            log.info("HandlerMapping: {} ({})", mapping.getClass().getSimpleName(), mapping)
        );
        
        // 4. Examining HandlerAdapters
        List<HandlerAdapter> handlerAdapters = applicationContext.getBeansOfType(HandlerAdapter.class).values().stream().toList();
        log.info("Number of HandlerAdapters: {}", handlerAdapters.size());
        
        handlerAdapters.forEach(adapter -> 
            log.info("HandlerAdapter: {} ({})", adapter.getClass().getSimpleName(), adapter)
        );
        
        // 5. Get the specific RequestMappingHandlerMapping (most commonly used)
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        assertNotNull(handlerMapping, "RequestMappingHandlerMapping should be available");
        
        // 6. Get the specific RequestMappingHandlerAdapter (most commonly used)
        RequestMappingHandlerAdapter handlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
        assertNotNull(handlerAdapter, "RequestMappingHandlerAdapter should be available");
    }
} 