package com.tzj.springdemo1.MvcFlowDemo.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * Custom ViewResolver implementation that demonstrates how to extend Spring's view resolution system.
 * This resolver handles view names that start with "custom:".
 */
@Slf4j
@Component
public class CustomViewResolver implements ViewResolver, Ordered {

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        log.info("=== [CustomViewResolver] Resolving view name: {} ===", viewName);
        
        // Only handle view names that start with our custom prefix
        if (!viewName.startsWith("custom:")) {
            log.info("Not a custom view, skipping");
            return null; // Let other view resolvers handle it
        }
        
        // Extract the actual view name by removing the prefix
        String actualViewName = viewName.substring("custom:".length());
        log.info("Extracted actual view name: {}", actualViewName);
        
        // Create a custom view instance
        CustomView view = new CustomView(actualViewName);
        log.info("Created custom view: {}", view);
        log.info("===========================================");
        
        return view;
    }

    @Override
    public int getOrder() {
        // Higher priority than the default view resolvers
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
} 