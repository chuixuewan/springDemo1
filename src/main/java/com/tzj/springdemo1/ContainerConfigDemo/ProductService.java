package com.tzj.springdemo1.ContainerConfigDemo;

// src/main/java/com/example/demo/annotation/service/ProductService.java


import com.tzj.springdemo1.ContainerConfigDemo.Product;
import com.tzj.springdemo1.ContainerConfigDemo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // Marks this class as a service component
public class ProductService {
    
    private final ProductRepository productRepository;
    private final double taxRate;
    
    @Autowired // Injects the ProductRepository bean
    public ProductService(ProductRepository productRepository, 
                         @Value("${tax.rate:0.1}") double taxRate) { // Injects property with default value 0.1
        this.productRepository = productRepository;
        this.taxRate = taxRate;
        System.out.println("ProductService created with tax rate: " + taxRate);
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public double calculatePriceWithTax(Long productId) {
        Product product = getProductById(productId);
        if (product != null) {
            return product.getPrice() * (1 + taxRate);
        }
        return 0;
    }
}
