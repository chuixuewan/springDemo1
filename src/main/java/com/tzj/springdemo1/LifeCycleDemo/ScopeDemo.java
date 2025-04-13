package com.tzj.springdemo1.LifeCycleDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScopeDemo {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        
        System.out.println("=== 测试Singleton作用域 ===");
        Person singleton1 = (Person) context.getBean("singletonPerson");
        Person singleton2 = (Person) context.getBean("singletonPerson");
        System.out.println("singleton1 == singleton2: " + (singleton1 == singleton2));
        
        System.out.println("\n=== 测试Prototype作用域 ===");
        Person prototype1 = (Person) context.getBean("prototypePerson");
        Person prototype2 = (Person) context.getBean("prototypePerson");
        System.out.println("prototype1 == prototype2: " + (prototype1 == prototype2));
    }
}