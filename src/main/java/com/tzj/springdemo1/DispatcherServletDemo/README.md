# Spring MVC DispatcherServlet Demo

This demo project demonstrates how Spring MVC's core components work together to process web requests.

## Core Components

### 1. DispatcherServlet

The `DispatcherServlet` is the front controller in Spring MVC. It receives all incoming HTTP requests and delegates them to appropriate handlers. The main workflow is:

1. Receive an HTTP request
2. Find an appropriate handler using registered `HandlerMapping`s
3. Use an appropriate `HandlerAdapter` to execute the handler
4. Process the model and view result
5. Render the view and send the response

Key responsibilities:
- Coordinating the entire request handling process
- Managing exceptions during request processing
- Selecting appropriate view renderers
- Resolving locale information and themes

### 2. HandlerMapping

`HandlerMapping` is responsible for mapping a request to a handler. Spring provides several implementations:

1. **RequestMappingHandlerMapping** (default): Maps requests to `@RequestMapping` annotated methods
2. **SimpleUrlHandlerMapping**: Maps URLs to controllers using explicit URL path patterns
3. **BeanNameUrlHandlerMapping**: Maps URLs to controllers based on bean names

In our demo:
- We created `CustomHandlerMapping` that maps specific URLs to our custom handler objects
- It has a higher priority than the default mapping to show how multiple mappings can coexist
- It demonstrates the basic interface methods that all handler mappings implement

### 3. HandlerAdapter

`HandlerAdapter` is responsible for executing handlers matched by `HandlerMapping`. Key implementations:

1. **RequestMappingHandlerAdapter** (default): Invokes controller methods annotated with `@RequestMapping`
2. **HttpRequestHandlerAdapter**: Adapts to `HttpRequestHandler` interface
3. **SimpleControllerHandlerAdapter**: Adapts to `Controller` interface

In our demo:
- We created `CustomHandlerAdapter` that knows how to handle our `CustomHandler` objects
- It demonstrates how to directly write to the response output
- It shows the adapter pattern in action - adapting our handler to the DispatcherServlet's expectations

### 4. Model, View and ViewResolver

- **Model**: Contains attributes to be displayed in the view
- **View**: Responsible for rendering content (HTML, JSON, etc.)
- **ViewResolver**: Resolves view names to actual View implementations

## Request Processing Flow

1. Client sends HTTP request to `DispatcherServlet`
2. `DispatcherServlet` consults `HandlerMapping`s to find the appropriate handler
3. `HandlerMapping` returns a `HandlerExecutionChain` containing the handler and any interceptors
4. `DispatcherServlet` calls appropriate `HandlerAdapter`
5. `HandlerAdapter` executes the handler (controller method or custom handler)
6. Handler returns a `ModelAndView` (or directly handles the response in some cases)
7. If needed, `ViewResolver` resolves the view name to an actual `View`
8. `View` renders the model data to the response

## Running the Demo Tests

Our demo includes two test classes:
1. `DispatcherServletDemo` - Shows the component beans available in the Spring context
2. `CustomDispatcherServletTest` - Demonstrates custom and regular handler behavior

The tests showcase the entire request processing flow through the Spring MVC architecture. 