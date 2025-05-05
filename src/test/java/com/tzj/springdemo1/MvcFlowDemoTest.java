package com.tzj.springdemo1;

import com.tzj.springdemo1.MvcFlowDemo.config.MvcFlowDemoTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Test class for the MVC Flow Demo that shows the interaction between various Spring MVC components.
 */
@Slf4j
@SpringBootTest(classes = MvcFlowDemoTestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("mvc-flow-demo")
public class MvcFlowDemoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testBasicFlow() throws Exception {
        log.info("\n\n========== Testing Basic Flow ==========\n");
        
        MvcResult result = mockMvc.perform(get("/flow/basic"))
                .andExpect(status().isOk())
                .andExpect(view().name("demo"))
                .andReturn();
        
        log.info("View Content: {}", result.getResponse().getContentAsString());
    }
    
    @Test
    public void testJsonFlow() throws Exception {
        log.info("\n\n========== Testing JSON Flow ==========\n");
        
        mockMvc.perform(get("/flow/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("This is a JSON response"))
                .andExpect(jsonPath("$.handledBy").value("DemoController.jsonDemo"))
                .andReturn();
    }
    
    @Test
    public void testEntityFlow() throws Exception {
        log.info("\n\n========== Testing ResponseEntity Flow ==========\n");
        
        mockMvc.perform(get("/flow/entity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("This is a ResponseEntity response"))
                .andExpect(jsonPath("$.handledBy").value("DemoController.entityDemo"))
                .andReturn();
    }
    
    @Test
    public void testExceptionFlow() throws Exception {
        log.info("\n\n========== Testing Exception Flow ==========\n");
        
        mockMvc.perform(get("/flow/exception"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("DEMO_ERROR"))
                .andReturn();
    }
    
    @Test
    public void testCustomViewFlow() throws Exception {
        log.info("\n\n========== Testing Custom View Flow ==========\n");
        
        MvcResult result = mockMvc.perform(get("/flow/custom-view"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        log.info("Content excerpt: {}", content.substring(0, Math.min(100, content.length())) + "...");
    }
} 