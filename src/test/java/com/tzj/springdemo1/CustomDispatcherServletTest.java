package com.tzj.springdemo1;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class CustomDispatcherServletTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCustomHandler() throws Exception {
        // Test the regular controller endpoint
        MvcResult result1 = mockMvc.perform(get("/demo/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from DispatcherServlet Demo!"))
                .andReturn();
        
        log.info("Regular controller response: {}", result1.getResponse().getContentAsString());
        
        // Test the custom handler endpoint
        MvcResult result2 = mockMvc.perform(get("/demo/custom"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is handled by the CustomHandlerMapping!"))
                .andReturn();
        
        log.info("Custom handler response: {}", result2.getResponse().getContentAsString());
        
        log.info("This demonstrates how both the standard RequestMappingHandlerMapping/Adapter and " +
                "our custom implementations can coexist in the same application.");
    }
} 