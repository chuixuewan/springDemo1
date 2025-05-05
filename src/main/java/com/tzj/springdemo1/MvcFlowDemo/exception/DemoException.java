package com.tzj.springdemo1.MvcFlowDemo.exception;

import lombok.Getter;

/**
 * Custom exception class for demonstrating exception handling in Spring MVC.
 */
@Getter
public class DemoException extends RuntimeException {
    
    private final String errorCode;
    
    public DemoException(String message) {
        this(message, "DEFAULT_ERROR");
    }
    
    public DemoException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public DemoException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
} 