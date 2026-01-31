package com.generaflyers.my_app_generate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration // Le dice a Spring: "Leeme al arrancar, soy configuración"
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Explicación:
        // Cuando alguien pida una URL tipo: http://localhost:8080/uploads/foto.jpg
        // Spring va a buscar el archivo físico en la carpeta "uploads" de tu disco.
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}