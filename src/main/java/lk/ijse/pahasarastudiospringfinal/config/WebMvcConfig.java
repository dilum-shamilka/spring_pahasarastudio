package lk.ijse.pahasarastudiospringfinal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve files from the "uploads" folder in your project root
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");

        // Ensure static resources are mapped correctly
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}