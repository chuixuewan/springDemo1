package com.tzj.springdemo1.LifeCycleDemo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class LifecycleBean implements InitializingBean, DisposableBean {
    private String message;
    
    public LifecycleBean() {
        System.out.println("1. 构造方法被调用");
    }
    
    public void setMessage(String message) {
        this.message = message;
        System.out.println("2. 属性设置: message = " + message);
    }
    
    // InitializingBean接口方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("4. InitializingBean的afterPropertiesSet方法被调用");
    }
    
    // 自定义初始化方法
    public void customInit() {
        System.out.println("5. 自定义初始化方法被调用");
    }
    
    // 注解方式的初始化
    @PostConstruct
    public void postConstruct() {
        System.out.println("3. @PostConstruct注解方法被调用");
    }
    
    // DisposableBean接口方法
    @Override
    public void destroy() throws Exception {
        System.out.println("7. DisposableBean的destroy方法被调用");
    }
    
    // 自定义销毁方法
    public void customDestroy() {
        System.out.println("8. 自定义销毁方法被调用");
    }
    
    // 注解方式的销毁
    @PreDestroy
    public void preDestroy() {
        System.out.println("6. @PreDestroy注解方法被调用");
    }
    
    public void showMessage() {
        System.out.println("Message: " + message);
    }
}
