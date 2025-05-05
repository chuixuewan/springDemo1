# Spring MVC Request Flow Explained

This document provides a detailed explanation of the Spring MVC request processing flow, with annotations showing when each component is initialized and executed.

## Initialization Flow (Application Startup)

```
Servlet Container Startup
  │
  ├─► ServletContextListener.contextInitialized() 
  │    [Application initialization events]
  │
  ├─► Load web.xml or WebApplicationInitializer
  │    [Configure servlets, filters, and listeners]
  │
  ├─► Create Root Application Context
  │    [Configured by ContextLoaderListener]
  │    
  ├─► Initialize Filters
  │    [In order defined in configuration]
  │    
  └─► Initialize DispatcherServlet
       │
       ├─► Create WebApplicationContext for servlet
       │
       └─► Initialize DispatcherServlet components:
            ├─► HandlerMappings
            ├─► HandlerAdapters
            ├─► ViewResolvers
            ├─► ExceptionResolvers
            ├─► MessageConverters
            └─► Other configured components
```

## Request Processing Flow (Runtime)

```
HTTP Request
  │
  ├─► HttpSessionListener.sessionCreated() [First request only]
  │
  ├─► Filter Chain (in order)
  │    │
  │    ├─► Filter1.doFilter() [pre-processing]
  │    │    │
  │    │    └─► chain.doFilter() [continues chain]
  │    │
  │    ├─► Filter2.doFilter() [pre-processing]
  │    │    │
  │    │    └─► chain.doFilter() [continues chain]
  │    │
  │    └─► FilterN.doFilter() [pre-processing]
  │         │
  │         └─► chain.doFilter() [passes to servlet]
  │
  ├─► DispatcherServlet.doService()
  │    │
  │    └─► DispatcherServlet.doDispatch()
  │         │
  │         ├─► HandlerMapping.getHandler()
  │         │    [Maps request to HandlerExecutionChain]
  │         │    
  │         ├─► HandlerExecutionChain
  │         │    ├─► Handler [Controller/Endpoint]
  │         │    └─► List<HandlerInterceptor> [Interceptors]
  │         │
  │         ├─► HandlerAdapter selection
  │         │    [Finds adapter that can handle the controller]
  │         │
  │         ├─► FOR EACH HandlerInterceptor:
  │         │    └─► interceptor.preHandle()
  │         │         [Before controller execution]
  │         │
  │         ├─► HandlerAdapter.handle()
  │         │    │
  │         │    ├─► Argument Resolution
  │         │    │    [Convert request to method arguments]
  │         │    │
  │         │    ├─► Controller method execution
  │         │    │    [Your business logic runs here]
  │         │    │
  │         │    └─► Return Value Handling
  │         │         [Process method return value]
  │         │
  │         ├─► FOR EACH HandlerInterceptor (reverse order):
  │         │    └─► interceptor.postHandle()
  │         │         [After controller execution, before view]
  │         │
  │         ├─► Exception? Yes → HandlerExceptionResolver
  │         │                     [Process the exception]
  │         │
  │         ├─► Render View or convert to HTTP Response
  │         │    │
  │         │    ├─► IF View name returned:
  │         │    │    │
  │         │    │    ├─► ViewResolver.resolveViewName()
  │         │    │    │    [Find the View implementation]
  │         │    │    │
  │         │    │    └─► View.render()
  │         │    │         [Generate response content]
  │         │    │
  │         │    └─► IF @ResponseBody or ResponseEntity:
  │         │         └─► HttpMessageConverter.write()
  │         │              [Convert object to response body]
  │         │
  │         └─► FOR EACH HandlerInterceptor (reverse order):
  │              └─► interceptor.afterCompletion()
  │                   [After view rendering, cleanup]
  │
  ├─► Filter Chain (reverse order)
  │    │
  │    ├─► FilterN.doFilter() [post-processing]
  │    │    [continues reverse chain]
  │    │
  │    ├─► Filter2.doFilter() [post-processing]
  │    │    [continues reverse chain]
  │    │
  │    └─► Filter1.doFilter() [post-processing]
  │         [completes filter chain]
  │
  └─► HTTP Response sent to client
```

## Key Processing Phases Explained

### 1. Filter Processing
Filters form a chain around the servlet container and process the request/response before and after the DispatcherServlet. They can modify the request/response objects or terminate the processing.

### 2. Handler Mapping
HandlerMapping components map the request to a handler by examining the URL, HTTP method, headers, etc. They also attach any registered interceptors to form a HandlerExecutionChain.

### 3. Interceptor Processing
Interceptors are similar to filters but operate within Spring MVC's context and have access to the handler and model information:
- `preHandle()`: Runs before the handler execution. Can short-circuit the flow.
- `postHandle()`: Runs after handler execution but before view rendering.
- `afterCompletion()`: Runs after view rendering completes.

### 4. Handler Execution
The handler (typically a controller method) is executed via the HandlerAdapter, which:
1. Resolves method arguments from the request
2. Invokes the method
3. Processes the return value

### 5. View Processing
If the handler returns a view name or ModelAndView, ViewResolvers convert it to a View object which then renders the response using the model data.

### 6. Exception Handling
If an exception occurs, HandlerExceptionResolvers attempt to handle it and generate an appropriate response.

## Demonstration Log Output Sample

For a typical request, our demo will produce logs that look like:

```
INITIALIZING APPLICATION
ServletContextListener: contextInitialized
StandardFilter: init
CustomFilter: init
LoggingInterceptor: initialized
CustomInterceptor: initialized

REQUEST PROCESSING
StandardFilter: doFilter PRE-PROCESSING
CustomFilter: doFilter PRE-PROCESSING
LoggingInterceptor: preHandle
CustomInterceptor: preHandle
DemoController: executing handler method
CustomInterceptor: postHandle
LoggingInterceptor: postHandle
ViewResolver: resolving view name 'demo'
CustomViewResolver: resolving view name 'demo'
View: rendering
CustomInterceptor: afterCompletion
LoggingInterceptor: afterCompletion
CustomFilter: doFilter POST-PROCESSING
StandardFilter: doFilter POST-PROCESSING
```

This log output makes it easy to visualize the entire flow and understand the order of component execution. 