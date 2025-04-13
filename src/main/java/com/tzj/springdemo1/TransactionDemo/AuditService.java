package com.tzj.springdemo1.TransactionDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AuditService {
    
    @Autowired
    private TransactionRecordMapper transactionRepository;
    
    // Will always execute in a separate transaction
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logTransferError(String fromAccount, String toAccount, 
                                BigDecimal amount, String errorMessage) {
        System.out.println("Logging transfer error in separate transaction");
        TransactionRecord errorLog = new TransactionRecord(
            fromAccount, toAccount, amount, "ERROR: " + errorMessage);
        transactionRepository.insert(errorLog);
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logSuccessfulTransfer(String fromAccount, String toAccount, BigDecimal amount) {
        System.out.println("Logging successful transfer in separate transaction");
        TransactionRecord auditLog = new TransactionRecord(
            fromAccount, toAccount, amount, "AUDIT: Transfer successful");
        transactionRepository.insert(auditLog);
    }
}