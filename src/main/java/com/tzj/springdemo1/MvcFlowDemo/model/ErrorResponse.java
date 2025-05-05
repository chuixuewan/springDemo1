package com.tzj.springdemo1.MvcFlowDemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Model class representing the error response data structure.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
    private String path;
    private int status;
    
    // Static factory method for quick creation
    public static ErrorResponse of(String message, String errorCode, String path, int status) {
        return ErrorResponse.builder()
                .message(message)
                .errorCode(errorCode)
                .timestamp(LocalDateTime.now())
                .path(path)
                .status(status)
                .build();
    }
} 