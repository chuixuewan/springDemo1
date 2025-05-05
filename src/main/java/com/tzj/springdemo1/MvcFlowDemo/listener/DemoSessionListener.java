package com.tzj.springdemo1.MvcFlowDemo.listener;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * A demonstration HttpSessionListener that logs session creation and destruction events.
 */
@Slf4j
@Component
public class DemoSessionListener implements HttpSessionListener {
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("=== DemoSessionListener: Session Created ===");
        log.info("Session ID: {}", se.getSession().getId());
        log.info("Creation Time: {}", se.getSession().getCreationTime());
        log.info("=========================================");
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("=== DemoSessionListener: Session Destroyed ===");
        log.info("Session ID: {}", se.getSession().getId());
        log.info("===========================================");
    }
} 