# Spring MVC Request Flow

## DispatcherServlet Request Processing Flow

```
 [Client]
    |
    | HTTP Request
    v
[DispatcherServlet] ----------------+
    |                               |
    | 1. Request received           |
    |                               |
    | 2. Process handler mappings   |
    v                               |
[HandlerMapping]                    |
    |                               |
    | Returns HandlerExecutionChain |
    | (Handler + Interceptors)      |
    v                               |
[DispatcherServlet] ----------------+
    |                               |
    | 3. Apply PreHandle interceptors|
    |                               |
    | 4. Find appropriate adapter   |
    v                               |
[HandlerAdapter]                    |
    |                               |
    | 5. Handle method invocation   |
    v                               |
[Handler/Controller]                |
    |                               |
    | Returns ModelAndView          |
    v                               |
[DispatcherServlet] ----------------+
    |                               |
    | 6. Apply PostHandle interceptors|
    |                               |
    | 7. Resolve view               |
    v                               |
[ViewResolver]                      |
    |                               |
    | Returns View object           |
    v                               |
[DispatcherServlet] ----------------+
    |                               |
    | 8. Render view                |
    v                               |
[View]                              |
    |                               |
    | Rendered output               |
    v                               |
[DispatcherServlet] ----------------+
    |                               |
    | 9. Apply AfterCompletion      |
    | interceptors                  |
    |                               |
    | 10. Return response           |
    v                               |
 [Client]
```

## Detailed Steps

### 1. Request Dispatching

When a request comes in, the `DispatcherServlet` acts as the central entry point:

```java
// This is a simplified version of what happens in DispatcherServlet.doDispatch()
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
        // 1. Find a handler for the request
        HandlerExecutionChain mappedHandler = getHandler(request);
        
        if (mappedHandler == null) {
            noHandlerFound(request, response);
            return;
        }
        
        // 2. Get the appropriate handler adapter
        HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
        
        // 3. Apply preHandle interceptors
        if (!mappedHandler.applyPreHandle(request, response)) {
            return;
        }
        
        // 4. Execute the handler via the adapter
        ModelAndView mv = ha.handle(request, response, mappedHandler.getHandler());
        
        // 5. Apply postHandle interceptors
        mappedHandler.applyPostHandle(request, response, mv);
        
        // 6. Process the DispatcherServlet's HandlerExceptionResolvers
        processDispatchResult(request, response, mappedHandler, mv, dispatchException);
    } finally {
        // 7. Apply afterCompletion callbacks
        mappedHandler.triggerAfterCompletion(request, response, null);
    }
}
```

### 2. Handler Mapping and Adapter Selection

1. **HandlerMapping Selection**: The `DispatcherServlet` consults each registered `HandlerMapping` in order of priority until one returns a non-null handler.

2. **HandlerAdapter Selection**: The `DispatcherServlet` finds the appropriate adapter by calling the `supports(handler)` method on each registered `HandlerAdapter`.

### 3. Handler Execution

The `HandlerAdapter` is responsible for:
1. Extracting information from the request
2. Converting parameters to the format the handler expects
3. Invoking the handler with the correct arguments
4. Processing the handler's return value into a `ModelAndView`

For `@Controller` classes, the `RequestMappingHandlerAdapter` does this work, inspecting the handler method signature, resolving arguments, and invoking the method.

### 4. View Resolution and Rendering

1. **View Resolution**: If a view name is returned, the `ViewResolver` resolves it to a concrete `View` implementation.

2. **View Rendering**: The view is responsible for merging the model data with a template or generating the output format (JSON, XML, HTML, etc.).

### 5. Exception Handling

If an exception occurs at any point in the process, the `DispatcherServlet` delegates to registered `HandlerExceptionResolver` implementations to process the exception and potentially produce an error view.

## Our Demo Implementation

In our demo:
1. We've shown both the standard and custom versions of handlers and adapters
2. The test cases demonstrate the complete flow through this architecture
3. Custom components help to understand the extension points in Spring MVC 