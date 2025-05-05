package com.tzj.springdemo1.MvcFlowDemo.controller;

import com.tzj.springdemo1.MvcFlowDemo.exception.DemoException;
import com.tzj.springdemo1.MvcFlowDemo.model.DemoResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * Main controller for demonstrating the Spring MVC request flow.
 */
@Slf4j
@Controller
@RequestMapping("/flow")
public class DemoController {

    /**
     * Basic demo endpoint that returns a simple view.
     */
    @GetMapping("/basic")
    public String basicDemo(Model model, HttpServletRequest request) {
        log.info("=== [DemoController] Handling /flow/basic ===");
        
        model.addAttribute("message", "Hello from Spring MVC Flow Demo!");
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("path", request.getRequestURI());
        
        log.info("Added attributes to model: {}", model.asMap());
        log.info("Returning view name: 'demo'");
        log.info("=====================================");
        
        return "demo"; // View name
    }
    
    /**
     * REST API endpoint that returns JSON.
     */
    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DemoResponse jsonDemo(HttpServletRequest request) {
        log.info("=== [DemoController] Handling /flow/json ===");
        
        DemoResponse response = DemoResponse.of(
                "This is a JSON response",
                "DemoController.jsonDemo",
                request.getRequestURI()
        );
        
        log.info("Created response object: {}", response);
        log.info("=====================================");
        
        return response;
    }
    
    /**
     * ResponseEntity demo that demonstrates direct response handling.
     */
    @GetMapping("/entity")
    public ResponseEntity<DemoResponse> entityDemo(HttpServletRequest request) {
        log.info("=== [DemoController] Handling /flow/entity ===");
        
        DemoResponse response = DemoResponse.of(
                "This is a ResponseEntity response",
                "DemoController.entityDemo",
                request.getRequestURI()
        );
        
        log.info("Created response object: {}", response);
        log.info("Returning ResponseEntity");
        log.info("=====================================");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Exception demo that throws a custom exception.
     */
    @GetMapping("/exception")
    public String exceptionDemo() {
        log.info("=== [DemoController] Handling /flow/exception ===");
        log.info("About to throw DemoException");
        
        throw new DemoException("This is a demonstration exception", "DEMO_ERROR");
    }
    
    /**
     * Custom view demo that will be handled by our CustomViewResolver.
     */
    @GetMapping("/custom-view")
    public String customViewDemo(Model model, HttpServletRequest request) {
        log.info("=== [DemoController] Handling /flow/custom-view ===");
        
        model.addAttribute("message", "This view will be handled by CustomViewResolver");
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("path", request.getRequestURI());
        
        log.info("Added attributes to model: {}", model.asMap());
        log.info("Returning view name: 'custom:demo'");
        log.info("=====================================");
        
        return "custom:demo"; // Custom view prefix
    }
} 