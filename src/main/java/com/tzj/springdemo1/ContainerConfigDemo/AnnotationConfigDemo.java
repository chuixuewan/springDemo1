package com.tzj.springdemo1.ContainerConfigDemo;

import com.tzj.springdemo1.ContainerConfigDemo.AppConfig;
import com.tzj.springdemo1.ContainerConfigDemo.Product;
import com.tzj.springdemo1.ContainerConfigDemo.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigDemo {
    public static void main(String[] args) {
        // Create Spring application context using annotation configuration
        AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);
        
        System.out.println("--- Annotation-based Configuration Demo ---");
        
        // Get ProductService bean
        ProductService productService = context.getBean(ProductService.class);
        
        // Use ProductService to get all products
        System.out.println("\nAll Products:");
        for (Product product : productService.getAllProducts()) {
            System.out.println(product);
        }
        
        // Calculate price with tax for a product
        Long productId = 1L;
        double priceWithTax = productService.calculatePriceWithTax(productId);
        Product product = productService.getProductById(productId);
        System.out.println("\nPrice calculation for " + product.getName() + ":");
        System.out.println("Original price: $" + product.getPrice());
        System.out.println("Price with tax: $" + String.format("%.2f", priceWithTax));
        
        // Close context
        context.close();
    }
}
