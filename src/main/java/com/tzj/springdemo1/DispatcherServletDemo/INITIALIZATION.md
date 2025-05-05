# DispatcherServlet Initialization Process

The `DispatcherServlet` initialization process is a critical part of Spring MVC. This document explains how the servlet is initialized and how it discovers and configures its components.

## Initialization Flow

1. **Servlet Container Initialization**
   - When a Spring Boot application starts, it creates an embedded servlet container
   - The container initializes the `DispatcherServlet` as configured in `WebMvcAutoConfiguration`

2. **DispatcherServlet Initialization**
   - The initialization happens in `DispatcherServlet.onRefresh()` method
   - It calls nine initialization methods to set up different components

```java
// Inside DispatcherServlet class
protected void onRefresh(ApplicationContext context) {
    initStrategies(context);
}

protected void initStrategies(ApplicationContext context) {
    initMultipartResolver(context);
    initLocaleResolver(context);
    initThemeResolver(context);
    initHandlerMappings(context);
    initHandlerAdapters(context);
    initHandlerExceptionResolvers(context);
    initRequestToViewNameTranslator(context);
    initViewResolvers(context);
    initFlashMapManager(context);
}
```

3. **HandlerMapping Initialization**
   - The `initHandlerMappings()` method discovers all `HandlerMapping` beans
   - Default implementation loads them from the application context
   - If none are defined, it loads the default mappings from DispatcherServlet.properties

```java
// Simplified version of initialization
private void initHandlerMappings(ApplicationContext context) {
    this.handlerMappings = null;

    // First, try to find handler mappings from the context
    if (this.detectAllHandlerMappings) {
        // Find all HandlerMapping beans in the ApplicationContext
        Map<String, HandlerMapping> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);
                
        if (!matchingBeans.isEmpty()) {
            this.handlerMappings = new ArrayList<>(matchingBeans.values());
            // Sort them based on Ordered implementation
            OrderComparator.sort(this.handlerMappings);
        }
    }
    
    // If no handler mappings are defined, use default ones from DispatcherServlet.properties
    if (this.handlerMappings == null) {
        this.handlerMappings = getDefaultStrategies(context, HandlerMapping.class);
    }
}
```

4. **HandlerAdapter Initialization**
   - Similar to HandlerMappings, all `HandlerAdapter` beans are discovered
   - If none are defined, defaults are loaded

## Default Strategies Configuration

Spring MVC loads default strategies from `DispatcherServlet.properties` resource file in the `org.springframework.web.servlet` package:

```properties
# Default HandlerMappings
org.springframework.web.servlet.HandlerMapping=org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,\
    org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping,\
    org.springframework.web.servlet.function.support.RouterFunctionMapping

# Default HandlerAdapters
org.springframework.web.servlet.HandlerAdapter=org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter,\
    org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter,\
    org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter,\
    org.springframework.web.servlet.function.support.HandlerFunctionAdapter
```

## Spring Boot Auto-Configuration

In Spring Boot, the `DispatcherServlet` is auto-configured in `WebMvcAutoConfiguration`:

```java
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
public class WebMvcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(DispatcherServlet.class)
    public DispatcherServlet dispatcherServlet(WebMvcProperties webMvcProperties) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setDispatchOptionsRequest(webMvcProperties.isDispatchOptionsRequest());
        dispatcherServlet.setDispatchTraceRequest(webMvcProperties.isDispatchTraceRequest());
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(webMvcProperties.isThrowExceptionIfNoHandlerFound());
        dispatcherServlet.setPublishEvents(webMvcProperties.isPublishRequestHandledEvents());
        dispatcherServlet.setEnableLoggingRequestDetails(webMvcProperties.isLogRequestDetails());
        return dispatcherServlet;
    }
    
    // Other configuration beans...
}
```

## Component Registration Order

The order of component registration is significant:

1. HandlerMappings and HandlerAdapters are sorted by the `Ordered` interface
2. Components with lower order values have higher priority
3. Custom components can declare a priority using `@Order` annotation or by implementing `Ordered`

In our demo:
- `CustomHandlerMapping` has priority `-1`, which is higher than the default `RequestMappingHandlerMapping` (which has priority `0`)
- This ensures our custom mapping is consulted before the standard one
- The same applies to our `CustomHandlerAdapter` 