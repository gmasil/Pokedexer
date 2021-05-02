package de.gmasil.collection.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import de.gmasil.collection.setup.InitialSetupInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private InitialSetupInterceptor initialSetupInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initialSetupInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/css/**").addResourceLocations("classpath:/static/public/css/",
                "classpath:/META-INF/resources/webjars/bootstrap/4.6.0/css/",
                "classpath:/META-INF/resources/slimselectjs/1.20.0/css/");
        registry.addResourceHandler("/public/js/**").addResourceLocations("classpath:/static/public/js/",
                "classpath:/META-INF/resources/webjars/bootstrap/4.6.0/js/",
                "classpath:/META-INF/resources/webjars/jquery/3.0.0/");
        registry.addResourceHandler("/public/slimselectjs/**").addResourceLocations("classpath:/slimselectjs/1.20.0/");
    }
}
