package com.tzj.springdemo1;
import com.tzj.springdemo1.AopDemo.AccountService;
import com.tzj.springdemo1.ContainerConfigDemo.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest
public class AopDemoTest {

    @Autowired
    private AccountService accountService;
    
    @Test
    public void testSuccessfulWithdrawal() {
        System.out.println("=== Testing successful withdrawal ===");
        double balance = accountService.withdraw("123", 30);
        System.out.println("Final balance: $" + balance);
    }
    
    @Test
    public void testLargeWithdrawal() {
        System.out.println("=== Testing large withdrawal ===");
        double balance = accountService.withdraw("123", 95);
        System.out.println("Final balance: $" + balance);
    }
    
    @Test
    public void testCreateAccountSuccess() {
        System.out.println("=== Testing account creation (success) ===");
        accountService.createAccount("456", 200);
    }
    
    @Test
    public void testCreateAccountFailure() {
        System.out.println("=== Testing account creation (failure) ===");
        try {
            accountService.createAccount("789", -50);
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
}
