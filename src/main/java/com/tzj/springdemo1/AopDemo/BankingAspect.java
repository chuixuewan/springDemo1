package com.tzj.springdemo1.AopDemo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// Aspect - The module containing advice for cross-cutting concerns
@Aspect
@Component
public class BankingAspect {

    // Pointcut - Expression matching specific join points
    @Pointcut("execution(* com.tzj.springdemo1.AopDemo.AccountService.*(..))")
    private void anyAccountOperation() {}
    
    @Pointcut("execution(* com.tzj.springdemo1.AopDemo.AccountService.withdraw(..))")
    private void withdrawOperation() {}

    // Before advice - Runs before the join point
    @Before("anyAccountOperation()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("\n[Before Advice] About to execute: " + 
                           joinPoint.getSignature().getName());
    }

    // After returning advice - Runs after successful completion
    @AfterReturning(pointcut = "withdrawOperation()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        System.out.println("[After Returning] Method returned: " + result);
    }

    // After throwing advice - Runs if method throws exception
    @AfterThrowing(pointcut = "anyAccountOperation()", throwing = "error")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error) {
        System.out.println("[After Throwing] Method threw exception: " + error.getMessage());
    }

    // After (finally) advice - Runs in all cases after method execution
    @After("anyAccountOperation()")
    public void afterAdvice(JoinPoint joinPoint) {
        System.out.println("[After] Method execution completed: " + 
                           joinPoint.getSignature().getName());
    }

    // Around advice - Surrounds the join point
    @Around("withdrawOperation()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("[Around] Before proceeding");
        
        // Get arguments
        Object[] args = joinPoint.getArgs();
        double amount = (double) args[1];
        
        // Example of modifying arguments
        if (amount > 50) {
            System.out.println("[Around] Large withdrawal detected - logging extra info");
        }
        
        // Proceed with execution (must be called manually in around advice)
        Object result = joinPoint.proceed();
        
        // Example of modifying the result
        double balance = (double) result;
        if (balance < 10) {
            System.out.println("[Around] Low balance warning!");
        }
        
        System.out.println("[Around] After proceeding");
        return result;
    }
}
