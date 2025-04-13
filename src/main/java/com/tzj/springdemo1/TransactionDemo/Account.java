package com.tzj.springdemo1.TransactionDemo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@TableName("account")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String accountNumber;
    private String ownerName;
    private BigDecimal balance;
    
    public Account(String accountNumber, String ownerName, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }
}
