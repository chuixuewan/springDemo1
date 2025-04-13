package com.tzj.springdemo1.ContainerConfigDemo;
import com.tzj.springdemo1.ContainerConfigDemo.Product;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // Marks this class as a repository component to be detected by component scan
public class ProductRepository {
    
    private Map<Long, Product> productMap = new HashMap<>();
    
    @PostConstruct // This method will be executed after bean initialization
    public void init() {
        System.out.println("ProductRepository: Initializing product data");
        // Add some sample products
        productMap.put(1L, new Product(1L, "Laptop", 999.99));
        productMap.put(2L, new Product(2L, "Phone", 699.99));
        productMap.put(3L, new Product(3L, "Tablet", 499.99));
    }
    
    @PreDestroy // This method will be executed before bean destruction
    public void cleanup() {
        System.out.println("ProductRepository: Cleaning up resources");
        productMap.clear();
    }
    
    public Product findById(Long id) {
        return productMap.get(id);
    }
    
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }
}
