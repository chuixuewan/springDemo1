package com.tzj.springdemo1.MvcFlowDemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Model class representing the response data structure for our demo.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoResponse {
    
    private String message;
    private String handledBy;
    private LocalDateTime timestamp;
    private String requestPath;
    
    // Static factory method for quick creation
    public static DemoResponse of(String message, String handledBy, String requestPath) {
        return DemoResponse.builder()
                .message(message)
                .handledBy(handledBy)
                .timestamp(LocalDateTime.now())
                .requestPath(requestPath)
                .build();
    }
} 