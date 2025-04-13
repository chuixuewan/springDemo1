package com.tzj.springdemo1.ContainerConfigDemo;

// src/main/java/com/example/demo/annotation/config/AppConfig.java

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration // Marks this class as a configuration class
@ComponentScan(basePackages = "com.tzj.springdemo1") // Automatically scan and register components
@PropertySource("classpath:application.properties") // Load properties from application.properties
public class AppConfig {
    // No bean definitions needed here - they're all detected via component scan
}
