package com.tzj.springdemo1.MvcFlowDemo.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.View;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Custom View implementation that renders content directly to the response.
 * This demonstrates how Spring MVC's view system can be extended.
 */
@Slf4j
public class CustomView implements View {

    private final String viewName;
    
    public CustomView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public String getContentType() {
        return "text/html;charset=UTF-8";
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("=== [CustomView] Rendering view: {} ===", viewName);
        log.info("Model contents: {}", model);
        
        // Set content type
        response.setContentType(getContentType());
        
        // Get writer
        PrintWriter writer = response.getWriter();
        
        // Render HTML directly
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("  <title>Custom View Demo</title>");
        writer.println("  <style>");
        writer.println("    body { font-family: Arial, sans-serif; margin: 40px; line-height: 1.6; }");
        writer.println("    .container { border: 1px solid #ddd; padding: 20px; border-radius: 5px; }");
        writer.println("    .header { background-color: #f8f9fa; padding: 10px; margin-bottom: 20px; }");
        writer.println("    .content { padding: 10px; }");
        writer.println("    .footer { text-align: center; margin-top: 20px; font-size: 0.8em; color: #6c757d; }");
        writer.println("  </style>");
        writer.println("</head>");
        writer.println("<body>");
        
        writer.println("  <div class='container'>");
        writer.println("    <div class='header'>");
        writer.println("      <h1>Custom View Renderer</h1>");
        writer.println("      <p>This content was rendered by our CustomView implementation.</p>");
        writer.println("    </div>");
        
        writer.println("    <div class='content'>");
        writer.println("      <h2>Model Data:</h2>");
        writer.println("      <ul>");
        
        // Render model attributes
        for (Map.Entry<String, ?> entry : model.entrySet()) {
            writer.println("        <li><strong>" + entry.getKey() + ":</strong> " + entry.getValue() + "</li>");
        }
        
        writer.println("      </ul>");
        
        // Add custom information
        writer.println("      <h2>View Information:</h2>");
        writer.println("      <ul>");
        writer.println("        <li><strong>View Name:</strong> " + viewName + "</li>");
        writer.println("        <li><strong>Rendered At:</strong> " + 
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "</li>");
        writer.println("        <li><strong>Request URI:</strong> " + request.getRequestURI() + "</li>");
        writer.println("      </ul>");
        writer.println("    </div>");
        
        writer.println("    <div class='footer'>");
        writer.println("      <p>Spring MVC Flow Demo &copy; " + LocalDateTime.now().getYear() + "</p>");
        writer.println("    </div>");
        writer.println("  </div>");
        
        writer.println("</body>");
        writer.println("</html>");
        
        // Flush and close
        writer.flush();
        
        log.info("Custom view rendering completed");
        log.info("=======================================");
    }
} 