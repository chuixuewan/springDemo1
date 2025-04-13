package com.tzj.springdemo1;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.tzj.springdemo1.TransactionDemo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionalDemoTest {

    @Autowired
    private BankService bankService;
    
    @Autowired
    private AccountMapper accountRepository;
    
    @Autowired
    private TransactionRecordMapper transactionRepository;
    
    @BeforeEach
    @Transactional
    public void setup() {
        // Clean previous data
        transactionRepository.delete(new QueryWrapper<>());
        accountRepository.delete(new QueryWrapper<>());
        
        // Set up test accounts
        accountRepository.insert(new Account("ACC001", "John Doe", new BigDecimal("1000.00")));
        accountRepository.insert(new Account("ACC002", "Jane Smith", new BigDecimal("2000.00")));
        accountRepository.insert(new Account("ACC003", "Bob Johnson", new BigDecimal("500.00")));
    }
    
    @Test
    public void testBasicTransfer() {
        // Arrange
        String fromAccount = "ACC001";
        String toAccount = "ACC002";
        BigDecimal amount = new BigDecimal("500.00");
        
        // Act
        bankService.transfer(fromAccount, toAccount, amount);
        
        // Assert
        Account account1 = accountRepository.findByAccountNumber(fromAccount);
        Account account2 = accountRepository.findByAccountNumber(toAccount);
        
        assertEquals(new BigDecimal("500.00"), account1.getBalance());
        assertEquals(new BigDecimal("2500.00"), account2.getBalance());
        
        QueryWrapper<TransactionRecord> query = new QueryWrapper<>();
        query.eq("from_account", fromAccount).or().eq("to_account", fromAccount);
        List<TransactionRecord> records = transactionRepository.selectList(query);
        assertTrue(records.size() > 0);
    }
    
    @Test
    public void testRollbackOnException() {
        // Arrange
        String fromAccount = "ACC003"; // Has 500.00
        String toAccount = "ACC002";
        BigDecimal amount = new BigDecimal("1000.00"); // More than balance
        
        // Act & Assert
        assertThrows(InsufficientFundsException.class, () -> {
            bankService.transfer(fromAccount, toAccount, amount);
        });
        
        // Verify rollback occurred
        Account account = accountRepository.findByAccountNumber(fromAccount);
        assertEquals(new BigDecimal("500.00"), account.getBalance());
    }
    
    @Test
    public void testCustomRollbackRules() {
        // Arrange
        String fromAccount = "ACC001"; 
        String toAccount = "INVALID"; // Invalid account
        BigDecimal amount = new BigDecimal("100.00");
        
        int recordsBeforeCount = transactionRepository.selectList(new QueryWrapper<>()).size();
        
        // Act & Assert
        assertThrows(InvalidAccountException.class, () -> {
            bankService.transferWithRollbackRules(fromAccount, toAccount, amount);
        });
        
        // Verify transaction record was saved despite exception (noRollbackFor)
        int recordsAfterCount = transactionRepository.selectList(new QueryWrapper<>()).size();
        assertEquals(recordsBeforeCount + 1, recordsAfterCount);
    }
    
    @Test
    public void testReadOnlyTransaction() {
        // This just demonstrates the readOnly flag doesn't affect reads
        List<TransactionRecord> records = bankService.getAccountHistory("ACC001");
        assertNotNull(records);
    }
    
    @Test
    public void testSecureFundsTransferWithError() {
        // Arrange
        String fromAccount = "ACC001";
        String toAccount = "ACC002";
        BigDecimal amount = new BigDecimal("20000.00"); // Will trigger security check failure
        
        int recordsBeforeCount = transactionRepository.selectList(new QueryWrapper<>()).size();
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            bankService.secureFundsTransfer(fromAccount, toAccount, amount);
        });
        
        // Verify main transaction rolled back but audit log was saved
        Account account1 = accountRepository.findByAccountNumber(fromAccount);
        assertEquals(new BigDecimal("1000.00"), account1.getBalance()); // Unchanged
        
        int recordsAfterCount = transactionRepository.selectList(new QueryWrapper<>()).size();
        assertEquals(recordsBeforeCount + 1, recordsAfterCount); // Only error log saved
        
        // Verify the error log content
        List<TransactionRecord> records = transactionRepository.selectList(new QueryWrapper<>());
        boolean hasErrorLog = records.stream()
            .anyMatch(r -> r.getStatus().startsWith("ERROR"));
        assertTrue(hasErrorLog);
    }
    
    @Test
    public void testBatchTransfersWithTimeout() {
        // Create a list of transfers that will take longer than timeout
        List<TransferRequest> requests = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            requests.add(new TransferRequest("ACC001", "ACC002", new BigDecimal("10.00")));
        }
        
        // This may throw a transaction timeout exception
        try {
            bankService.processBatchTransfers(requests);
        } catch (Exception e) {
            System.out.println("Expected timeout exception: " + e.getMessage());
        }
    }
}