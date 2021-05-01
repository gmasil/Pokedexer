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
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/",
                "classpath:/META-INF/resources/webjars/bootstrap/4.3.1/css/",
                "classpath:/META-INF/resources/slimselectjs/1.20.0/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/",
                "classpath:/META-INF/resources/webjars/bootstrap/4.3.1/js/",
                "classpath:/META-INF/resources/webjars/popper.js/1.14.3/",
                "classpath:/META-INF/resources/webjars/jquery/3.0.0/",
                "classpath:/META-INF/resources/webjars/chartjs/2.7.3/");
        registry.addResourceHandler("/slimselectjs/**").addResourceLocations("classpath:/slimselectjs/1.20.0/");
    }
}
