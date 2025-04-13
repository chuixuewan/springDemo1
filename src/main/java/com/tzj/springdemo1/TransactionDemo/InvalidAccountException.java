package com.tzj.springdemo1.TransactionDemo;

public class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(String message) {
        super(message);
    }
}
