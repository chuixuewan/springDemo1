package com.tzj.springdemo1.LifeCycleDemo;


import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class BeanFactoryDemo {
    public static void main(String[] args) {
        // Using BeanFactory
        System.out.println("=== Using BeanFactory ===");
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new ClassPathResource("beans.xml"));

        // BeanFactory is lazy loading, Person not created yet
        System.out.println("BeanFactory initialized, but Bean not created yet");
        Person person1 = (Person) beanFactory.getBean("singletonPerson");
        System.out.println(person1);

        // Using ApplicationContext
        System.out.println("\n=== Using ApplicationContext ===");
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // ApplicationContext is eager loading, Person already created
        System.out.println("ApplicationContext initialized, Bean already created");
        Person person2 = (Person) context.getBean("singletonPerson");
        System.out.println(person2);
    }
}