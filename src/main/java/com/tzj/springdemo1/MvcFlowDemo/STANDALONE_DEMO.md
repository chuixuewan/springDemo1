# MVC Flow Demo - Standalone Application

This document explains how to run the MVC Flow Demo as a standalone application with a real servlet container, allowing you to test the servlet lifecycle events like listeners through a browser.

## Why a Standalone Application?

The original `MvcFlowDemoTest.java` uses `MockMvc` which doesn't create a real servlet container. This means:

1. `DemoContextListener` doesn't receive real ServletContext events
2. `DemoSessionListener` doesn't receive real HTTP session events

To observe these listeners in action, we need a real servlet container.

## Running the Standalone Application

### Method 1: Through IDE

1. Open `MvcFlowDemoApplication.java` in your IDE
2. Run it as a Java application
3. The application will start on port 8080

### Method 2: Using Maven

```bash
# Using Maven
mvn spring-boot:run -Dspring-boot.run.profiles=mvc-flow-demo -Dspring-boot.run.main-class=com.tzj.springdemo1.MvcFlowDemo.MvcFlowDemoApplication

# Using Maven wrapper
./mvnw spring-boot:run -Dspring-boot.run.profiles=mvc-flow-demo -Dspring-boot.run.main-class=com.tzj.springdemo1.MvcFlowDemo.MvcFlowDemoApplication
```

## Testing the Application

Once the application is running, you can access the following endpoints:

1. [Basic Demo](http://localhost:8080/flow/basic) - `http://localhost:8080/flow/basic`
2. [JSON Demo](http://localhost:8080/flow/json) - `http://localhost:8080/flow/json`
3. [ResponseEntity Demo](http://localhost:8080/flow/entity) - `http://localhost:8080/flow/entity`
4. [Exception Demo](http://localhost:8080/flow/exception) - `http://localhost:8080/flow/exception`
5. [Custom View Demo](http://localhost:8080/flow/custom-view) - `http://localhost:8080/flow/custom-view`

## Observing Listeners in Action

When you run the application, you'll see the following in the console logs:

1. **Application Startup**: `DemoContextListener` will log the ServletContext initialization
   ```
   === DemoContextListener: ServletContext initialized ===
   ```

2. **Session Creation**: When you access any endpoint for the first time, `DemoSessionListener` will log the session creation
   ```
   === DemoSessionListener: Session Created ===
   ```

3. **Session Destruction**: If you restart the application or wait for the session timeout (5 minutes), you'll see
   ```
   === DemoSessionListener: Session Destroyed ===
   ```

4. **Application Shutdown**: When you stop the application, `DemoContextListener` will log
   ```
   === DemoContextListener: ServletContext being destroyed ===
   ```

## Differences from MockMvc Testing

This standalone application:

1. Creates a real servlet container
2. Processes real HTTP requests
3. Creates real HTTP sessions
4. Triggers all servlet lifecycle events

This allows you to see the complete Spring MVC request processing flow, including listener events that are not triggered in MockMvc tests. 