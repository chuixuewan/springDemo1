package com.tzj.springdemo1.LifeCycleDemo;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LifecycleDemo {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("lifecycle-beans.xml");
        
        System.out.println("\n=== Bean已创建并初始化 ===");
        LifecycleBean bean = context.getBean("lifecycleBean", LifecycleBean.class);
        bean.showMessage();
        
        

        System.out.println("\n=== Bean已创建并初始化 ===");
        LifecycleBean bean2 = context.getBean("lifecycleBean2", LifecycleBean.class);
        bean2.showMessage();
        
        System.out.println("\n=== 关闭容器，触发销毁回调 ===");
        context.close();
    }
}
