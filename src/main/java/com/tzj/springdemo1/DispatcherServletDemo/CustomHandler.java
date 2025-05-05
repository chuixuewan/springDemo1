package com.tzj.springdemo1.DispatcherServletDemo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A simple custom handler that will be used with our CustomHandlerMapping
 * and CustomHandlerAdapter
 */
@Getter
@AllArgsConstructor
public class CustomHandler {
    private final String response;
} 