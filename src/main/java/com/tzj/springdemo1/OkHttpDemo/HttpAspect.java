package com.tzj.springdemo1.OkHttpDemo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Aspect for HTTP operations using OkHttp
 */
@Aspect
@Component
public class HttpAspect {

    // Pointcut for all methods in HttpService
    @Pointcut("execution(* com.tzj.springdemo1.OkHttpDemo.HttpService.*(..))")
    private void anyHttpOperation() {}
    
    // Pointcut for GET operations
    @Pointcut("execution(* com.tzj.springdemo1.OkHttpDemo.HttpService.performGet(..))")
    private void getOperation() {}
    
    // Pointcut for POST operations
    @Pointcut("execution(* com.tzj.springdemo1.OkHttpDemo.HttpService.performPost*(..))")
    private void postOperation() {}
    
    // Before advice - Runs before the HTTP operation
    @Before("anyHttpOperation()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("\n[Before HTTP] About to execute: " + 
                           joinPoint.getSignature().getName());
        
        // Print the first argument (URL) if it exists
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof String) {
            System.out.println("[Before HTTP] URL: " + args[0]);
        }
    }
    
    // After returning advice - Runs after successful completion
    @AfterReturning(pointcut = "anyHttpOperation()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        System.out.println("[After HTTP Success] Method returned response of length: " + 
                          (result instanceof String ? ((String) result).length() : "unknown"));
    }
    
    // After throwing advice - Runs if method throws exception
    @AfterThrowing(pointcut = "anyHttpOperation()", throwing = "error")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error) {
        System.out.println("[After HTTP Error] Request failed: " + error.getMessage());
    }
    
    // After (finally) advice - Runs in all cases after method execution
    @After("anyHttpOperation()")
    public void afterAdvice(JoinPoint joinPoint) {
        System.out.println("[After HTTP] Request completed: " + 
                           joinPoint.getSignature().getName());
    }
    
    // Around advice for GET operations
    @Around("getOperation()")
    public Object aroundGetAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("[Around GET] Before sending request");
        
        long startTime = System.currentTimeMillis();
        
        // Proceed with execution
        Object result = joinPoint.proceed();
        
        long endTime = System.currentTimeMillis();
        System.out.println("[Around GET] Request took " + (endTime - startTime) + "ms");
        
        return result;
    }
    
    // Around advice for POST operations
    @Around("postOperation()")
    public Object aroundPostAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("[Around POST] Before sending request");
        
        // Get arguments
        Object[] args = joinPoint.getArgs();
        
        // Log request info based on method name
        String methodName = joinPoint.getSignature().getName();
        if (methodName.equals("performPostJson") && args.length > 1) {
            System.out.println("[Around POST] JSON Body length: " + 
                              (args[1] instanceof String ? ((String) args[1]).length() : "unknown"));
        } else if (methodName.equals("performPostForm") && args.length > 1) {
            System.out.println("[Around POST] Form parameters count: " + 
                              (args[1] != null ? "provided" : "none"));
        }
        
        // Proceed with execution
        Object result = joinPoint.proceed();
        
        System.out.println("[Around POST] Request completed");
        return result;
    }
} 