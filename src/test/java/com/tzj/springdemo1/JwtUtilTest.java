package com.tzj.springdemo1;

import static org.junit.jupiter.api.Assertions.*;

import com.tzj.springdemo1.JwtDemo.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Test
    public void testGenerateAndValidateToken() {
        // Generate token
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        
        // Validate token
        assertNotNull(token);
        System.out.println(token);
        System.out.println(jwtUtil.extractExpirationDate(token));
        System.out.println(jwtUtil.extractUsername(token));
        assertEquals(username, jwtUtil.extractUsername(token));
        assertTrue(jwtUtil.validateToken(token, username));
    }
    
    @Test
    public void testTokenWithClaims() {
        // Prepare claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ADMIN");
        claims.put("permissions", "READ,WRITE");
        
        // Generate token with claims
        String username = "adminuser";
        String token = jwtUtil.generateToken(username, claims);
        
        // Validate custom claims
        assertEquals(username, jwtUtil.extractUsername(token));
        assertEquals("ADMIN", jwtUtil.extractClaim(token, c -> c.get("role", String.class)));
        assertEquals("READ,WRITE", jwtUtil.extractClaim(token, c -> c.get("permissions", String.class)));
    }
    
    @Test
    public void testInvalidToken() {
        String username = "user1";
        String token = jwtUtil.generateToken(username);
        
        // Test with wrong username
        assertFalse(jwtUtil.validateToken(token, "wronguser"));
        
        // Test with malformed token
        assertFalse(jwtUtil.validateToken("invalid.token.string", username));
    }
}
