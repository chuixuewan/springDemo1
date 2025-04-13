package com.tzj.springdemo1.LifeCycleDemo;

public class Person {
    private String name;
    private int age;
    
    public Person() {
        System.out.println("Person实例被创建");
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }
}
