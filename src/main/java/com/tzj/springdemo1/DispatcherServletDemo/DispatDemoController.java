package com.tzj.springdemo1.DispatcherServletDemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A simple controller to demonstrate DispatcherServlet, HandlerMapping, and HandlerAdapter functionality
 */
@RestController
@RequestMapping("/demo")
public class DispatDemoController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from DispatcherServlet Demo!";
    }
    
    @GetMapping("/info")
    public String info() {
        return "This is a demo controller for learning Spring MVC components";
    }
} 