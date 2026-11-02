package com.humanresource.humanresourcemanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                // Return type: ResourceHandlerRegistration
                // Why: Defines URL pattern that browser will call
                .addResourceLocations("file:" + uploadDir + "/");
        // Return type: ResourceHandlerRegistration
        // Why: Tells Spring to look in filesystem (not classpath)
        // "file:" prefix means external directory
    }

}