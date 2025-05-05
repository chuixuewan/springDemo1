# Spring MVC Request Flow Demo

This demo provides a comprehensive demonstration of the Spring MVC request processing flow, showing how various components interact when handling HTTP requests.

## Components Demonstrated

### Container Level
- **ServletContextListener** - Initialized during application startup
- **HttpSessionListener** - Monitors session creation and destruction

### Filter Level
- **StandardFilter** - A regular Spring filter implementation
- **CustomFilter** - A custom filter implementation showing filter chain processing

### Servlet Level
- **DispatcherServlet** - Spring's front controller (auto-configured)

### Spring MVC Components
- **HandlerInterceptors**
  - **LoggingInterceptor** - Logs request information
  - **CustomInterceptor** - Demonstrates all interceptor methods
  
- **Controllers**
  - **DemoController** - Standard controller with various endpoint types
  - **ExceptionDemoController** - Demonstrates exception handling

- **HandlerExceptionResolvers**
  - **CustomExceptionResolver** - Custom implementation for specific exception types
  
- **ViewResolvers**
  - **Standard ViewResolver** - Regular Spring view resolver
  - **CustomViewResolver** - Custom implementation to demonstrate view resolution

- **MessageConverters**
  - **CustomMessageConverter** - Custom implementation for specific media types

## Request Flow

The demo illustrates the following flow:

1. Client Request
2. Filter Processing (Pre)
3. DispatcherServlet Handling
4. Handler Mapping Selection
5. Interceptor Pre-Handle
6. Handler (Controller) Execution
7. Interceptor Post-Handle
8. View Resolution/Rendering
9. Interceptor After-Completion
10. Filter Processing (Post)
11. Response to Client

## How to Use

This demo includes various endpoints you can call to see different parts of the request flow:

- `/flow/basic` - Basic request flow with all components
- `/flow/view` - View-based response demonstration
- `/flow/json` - REST response with message conversion
- `/flow/exception` - Exception handling flow
- `/flow/custom-view` - Custom view resolver demonstration

Each endpoint will log the full execution flow, showing which components are involved and in what order.

## Implementation Details

Each component in the flow adds log statements that clearly indicate when it starts and stops processing, making it easy to visualize the entire request lifecycle. 