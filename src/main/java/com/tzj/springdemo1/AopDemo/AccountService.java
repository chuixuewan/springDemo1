package com.tzj.springdemo1.AopDemo;

import org.springframework.stereotype.Service;

// Target object - The business service being advised
@Service
public class AccountService {
    
    public void createAccount(String accountId, double initialAmount) {
        System.out.println("Creating account: " + accountId + " with $" + initialAmount);
        // Business logic
        if (initialAmount < 0) {
            throw new IllegalArgumentException("Initial amount cannot be negative");
        }
    }
    
    public double withdraw(String accountId, double amount) {
        System.out.println("Withdrawing $" + amount + " from account: " + accountId);
        // Business logic
        return 100 - amount; // Simplified balance calculation
    }
}
