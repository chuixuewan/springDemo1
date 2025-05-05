package com.tzj.springdemo1.MvcFlowDemo;

import com.tzj.springdemo1.MvcFlowDemo.config.MvcFlowDemoTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Standalone application for running the MVC Flow Demo with a real servlet container.
 * This allows testing the listeners and other servlet components through a browser.
 */
@Slf4j
@SpringBootApplication
@Import(MvcFlowDemoTestConfig.class)
public class MvcFlowDemoApplication {

    public static void main(String[] args) {
        log.info("Starting MVC Flow Demo Application with a real servlet container...");
        
        // Set the active profile to mvc-flow-demo
        System.setProperty("spring.profiles.active", "mvc-flow-demo");
        
        SpringApplication.run(MvcFlowDemoApplication.class, args);
        
        log.info("MVC Flow Demo Application started successfully!");
        log.info("You can access the demo endpoints at: http://localhost:8080/flow/...");
        log.info("Available endpoints:");
        log.info("- http://localhost:8080/flow/basic");
        log.info("- http://localhost:8080/flow/json");
        log.info("- http://localhost:8080/flow/entity");
        log.info("- http://localhost:8080/flow/exception");
        log.info("- http://localhost:8080/flow/custom-view");
    }
} 