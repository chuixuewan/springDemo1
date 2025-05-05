package com.tzj.springdemo1.MvcFlowDemo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration class specifically for MvcFlowDemo to disable database-related auto-configuration
 * This allows running MvcFlowDemo tests without requiring a database connection
 */
@Configuration
@Profile("mvc-flow-demo")
@ComponentScan("com.tzj.springdemo1.MvcFlowDemo")
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class MvcFlowDemoTestConfig {
    // This class is intentionally empty. Its purpose is to provide configuration via annotations.
} 