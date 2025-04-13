package com.tzj.springdemo1.TransactionDemo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("transaction_record")
@Data
public class TransactionRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt;
    
    public TransactionRecord(){
        
    }

    public TransactionRecord(String fromAccount, String toAccount, BigDecimal amount, String status) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.status = status;
    }
    // Constructors, getters, setters (same as before, plus createdAt)
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
