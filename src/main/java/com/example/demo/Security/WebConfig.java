package com.example.demo.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Naya Dynamic Path: D:/Interview/My-locker-storage/documents/
        String uploadPath = "file:///D:/Interview/My-locker-storage/documents/";
        
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}