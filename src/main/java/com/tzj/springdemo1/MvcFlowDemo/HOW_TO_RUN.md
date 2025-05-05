# How to Run the Spring MVC Flow Demo

This document provides instructions on how to run and observe the comprehensive Spring MVC request processing flow demo.

## Prerequisites

- Java 17 or higher
- Maven 3.6+ or use the included Maven wrapper

## Running the Test

The demo is designed to be run through the test class which executes different request paths to demonstrate the various components of Spring MVC.

### 1. Build the Project

First, build the project to ensure all dependencies are downloaded:

```bash
# Using Maven
mvn clean install

# Using Maven wrapper
./mvnw clean install
```

### 2. Run the Test

Run the test class that demonstrates different flows:

```bash
# Run the specific test
mvn test -Dtest=MvcFlowDemoTest
```

### 3. Understanding the Output

The test will execute five different request flows, each demonstrating different aspects of Spring MVC:

1. **Basic Flow** (`/flow/basic`): Shows a standard view-based flow
2. **JSON Flow** (`/flow/json`): Demonstrates REST API with @ResponseBody
3. **ResponseEntity Flow** (`/flow/entity`): Shows direct ResponseEntity handling
4. **Exception Flow** (`/flow/exception`): Demonstrates custom exception handling
5. **Custom View Flow** (`/flow/custom-view`): Shows a custom view resolver in action

### 4. Observing Component Interactions

Each request will produce detailed logs showing the sequence of component execution:

1. **Filter Execution**: You'll see both filters executing in order
2. **Interceptor Methods**: All interceptor methods will print logs at each phase
3. **Handler Execution**: The controller method execution will be logged
4. **View Resolution**: The view resolution process will be logged
5. **Response Generation**: The response handling will be logged

Look for log messages containing:
- `[LoggingFilter]`
- `[CustomFilter]`
- `[LoggingInterceptor]`
- `[CustomInterceptor]`
- `[DemoController]`
- `[CustomViewResolver]`
- `[CustomView]`
- `[CustomExceptionResolver]`

## Key Components to Examine

The demo includes custom implementations of various Spring MVC components:

1. **Listeners**:
   - `DemoContextListener`: Logs application startup/shutdown events
   - `DemoSessionListener`: Logs session creation/destruction events

2. **Filters**:
   - `LoggingFilter`: Standard Spring filter using OncePerRequestFilter
   - `CustomFilter`: Custom implementation using the Filter interface directly

3. **Interceptors**:
   - `LoggingInterceptor`: Logs handler method execution details
   - `CustomInterceptor`: Shows how to modify the request and response

4. **Controllers**:
   - `DemoController`: Contains various endpoints demonstrating different response types

5. **ExceptionHandler**:
   - `CustomExceptionResolver`: Shows custom exception resolution

6. **ViewResolver**:
   - `CustomViewResolver`: Resolves custom view names (`custom:xxx`)

7. **View**:
   - `CustomView`: Renders HTML directly to the response

## Expected Output

Each component will print detailed logs showing when it's being invoked and what it's doing. This allows you to observe the complete request processing flow and understand how the components work together.

For example, for a request to `/flow/basic`, you'll see:

1. Filter pre-processing
2. Interceptor preHandle
3. Controller method execution
4. Interceptor postHandle
5. View resolution and rendering
6. Interceptor afterCompletion
7. Filter post-processing

This demonstrates the entire Spring MVC request processing lifecycle in action. 