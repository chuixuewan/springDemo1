# How to Run the DispatcherServlet Demo

This document provides instructions on how to run the demos to understand DispatcherServlet, HandlerMapping, and HandlerAdapter.

## Prerequisites

- Java 17 or higher
- Maven 3.6+ or use the included Maven wrapper

## Running the Tests

### 1. Build the Project

First, build the project to ensure all dependencies are downloaded:

```bash
# Using Maven
mvn clean install

# Using Maven wrapper
./mvnw clean install
```

### 2. Run the Tests

You can run the tests using Maven:

```bash
# Run all tests
mvn test

# Run a specific test
mvn test -Dtest=CustomDispatcherServletTest
```

### 3. Available Test Classes

- **CustomDispatcherServletTest**: Demonstrates how both the standard controller and custom handler work
- **HandlerMethodDemoTest**: Explores the internal structure of HandlerMethod objects
- **DispatcherServletDemo**: Shows the Spring MVC components in the application context

### 4. Understanding the Output

When running the tests, look for log messages that show:

1. The registered HandlerMappings and their order
2. The registered HandlerAdapters and their order
3. The request flow through DispatcherServlet
4. How controller methods are processed

Example log output from `CustomDispatcherServletTest`:

```
Regular controller response: Hello from DispatcherServlet Demo!
Custom handler response: This is handled by the CustomHandlerMapping!
```

## Key Files to Examine

- **DemoController.java**: A standard Spring MVC controller
- **CustomHandlerMapping.java**: A custom HandlerMapping implementation
- **CustomHandler.java**: A custom handler object
- **CustomHandlerAdapter.java**: A custom HandlerAdapter implementation
- **README.md**: Overview of Spring MVC architecture
- **REQUEST_FLOW.md**: Detailed explanation of the request flow
- **INITIALIZATION.md**: Explanation of DispatcherServlet initialization

## Understanding the Demo Architecture

1. **/demo/hello** and **/demo/info** are handled by the standard `DemoController` using the default RequestMappingHandlerMapping and RequestMappingHandlerAdapter

2. **/demo/custom** is handled by our custom handler through CustomHandlerMapping and CustomHandlerAdapter

3. The tests validate both paths work correctly and show the internal mechanisms of Spring MVC

This demonstrates how Spring MVC's extension points can be used to customize the request handling process. 