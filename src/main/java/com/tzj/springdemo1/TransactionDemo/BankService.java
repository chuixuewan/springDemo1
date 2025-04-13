package com.tzj.springdemo1.TransactionDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankService {

    @Autowired
    private AccountMapper accountRepository;
    
    @Autowired
    private TransactionRecordMapper transactionRepository;
    
    @Autowired
    private AuditService auditService;

    // Basic transaction - default settings
    @Transactional
    public void transfer(String fromAccountNum, String toAccountNum, BigDecimal amount) {
        System.out.println("Starting basic transfer transaction");
        
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNum);
        Account toAccount = accountRepository.findByAccountNumber(toAccountNum);
        
        validateTransfer(fromAccount, toAccount, amount);
        
        // Update balances
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        
        // Save accounts
        accountRepository.updateById(fromAccount);
        accountRepository.updateById(toAccount);
        
        // Record transaction
        TransactionRecord record = new TransactionRecord(
            fromAccountNum, toAccountNum, amount, "COMPLETED");
        transactionRepository.insert(record);
        
        System.out.println("Basic transfer transaction completed");
    }
    
    // Fine-tuned with specific rollback rules
    @Transactional(
        rollbackFor = {InsufficientFundsException.class},
        noRollbackFor = {InvalidAccountException.class}
    )
    public void transferWithRollbackRules(String fromAccountNum, String toAccountNum, BigDecimal amount) {
        System.out.println("Starting transfer with custom rollback rules");
        
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNum);
        Account toAccount = accountRepository.findByAccountNumber(toAccountNum);
        
        // Check if accounts exist
        if (fromAccount == null || toAccount == null) {
            // Transaction won't roll back for this exception
            TransactionRecord failedRecord = new TransactionRecord(
                fromAccountNum, toAccountNum, amount, "FAILED - INVALID ACCOUNT");
            transactionRepository.insert(failedRecord);
            throw new InvalidAccountException("One of the accounts does not exist");
        }
        
        // Check sufficient funds
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            // Transaction will roll back for this exception
            throw new InsufficientFundsException("Insufficient funds in account: " + fromAccountNum);
        }
        
        // Execute transfer
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        
        accountRepository.updateById(fromAccount);
        accountRepository.updateById(toAccount);
        
        TransactionRecord record = new TransactionRecord(
            fromAccountNum, toAccountNum, amount, "COMPLETED");
        transactionRepository.insert(record);
        
        System.out.println("Transfer with custom rollback rules completed");
    }
    
    // Using read-only optimization
    @Transactional(readOnly = true)
    public List<TransactionRecord> getAccountHistory(String accountNumber) {
        System.out.println("Executing read-only transaction");
        return transactionRepository.findRecentTransactions(accountNumber);
    }
    
    // Custom isolation level
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BigDecimal getAccountBalance(String accountNumber) {
        System.out.println("Getting balance with SERIALIZABLE isolation");
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new InvalidAccountException("Account not found: " + accountNumber);
        }
        return account.getBalance();
    }
    
    // Setting timeout
    @Transactional(timeout = 5)
    public void processBatchTransfers(List<TransferRequest> requests) {
        System.out.println("Starting batch transfer with 5 second timeout");
        for (TransferRequest request : requests) {
            transfer(request.getFromAccount(), request.getToAccount(), request.getAmount());
            // Simulate time-consuming operation
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Batch transfer completed");
    }
    
    // Transaction with guaranteed logging (REQUIRES_NEW)
    @Transactional
    public void secureFundsTransfer(String fromAccountNum, String toAccountNum, BigDecimal amount) {
        System.out.println("Starting secure transfer with audit");
        
        try {
            Account fromAccount = accountRepository.findByAccountNumber(fromAccountNum);
            Account toAccount = accountRepository.findByAccountNumber(toAccountNum);
            
            validateTransfer(fromAccount, toAccount, amount);
            
            // Update balances
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));
            
            accountRepository.updateById(fromAccount);
            accountRepository.updateById(toAccount);
            
            TransactionRecord record = new TransactionRecord(
                fromAccountNum, toAccountNum, amount, "COMPLETED");
            transactionRepository.insert(record);
            
            // Simulate potential error
            if (amount.compareTo(new BigDecimal("10000")) > 0) {
                throw new RuntimeException("Large transfer failed security check");
            }
            
        } catch (Exception e) {
            // This will be logged even if main transaction rolls back
            auditService.logTransferError(fromAccountNum, toAccountNum, amount, e.getMessage());
            throw e;
        }
        
        // Log successful transfer in separate transaction
        auditService.logSuccessfulTransfer(fromAccountNum, toAccountNum, amount);
        
        System.out.println("Secure transfer completed");
    }
    
    // Helper method
    private void validateTransfer(Account fromAccount, Account toAccount, BigDecimal amount) {
        if (fromAccount == null || toAccount == null) {
            throw new InvalidAccountException("One of the accounts does not exist");
        }
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in account: " + 
                                              fromAccount.getAccountNumber());
        }
    }
}
