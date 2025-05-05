package com.tzj.springdemo1.MvcFlowDemo.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * A demonstration ServletContextListener that logs application startup and shutdown events.
 */
@Slf4j
@Component
public class DemoContextListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("=== DemoContextListener: ServletContext initialized ===");
        log.info("Application Name: {}", sce.getServletContext().getServletContextName());
        log.info("Server Info: {}", sce.getServletContext().getServerInfo());
        log.info("========================================================");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("=== DemoContextListener: ServletContext being destroyed ===");
    }
} 