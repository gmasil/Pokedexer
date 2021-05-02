package de.gmasil.collection.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import de.gmasil.collection.setup.InitialSetupInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${setup.interceptor.enable:true}")
    private boolean enableSetupInterceptor;

    @Autowired
    private InitialSetupInterceptor initialSetupInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (enableSetupInterceptor) {
            registry.addInterceptor(initialSetupInterceptor);
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/css/**").addResourceLocations( //
                "classpath:/static/public/css/", //
                "classpath:/META-INF/resources/webjars/bootstrap/4.6.0/css/", //
                "classpath:/slimselectjs/1.20.0/");
        registry.addResourceHandler("/public/js/**").addResourceLocations( //
                "classpath:/static/public/js/", //
                "classpath:/META-INF/resources/webjars/bootstrap/4.6.0/js/", //
                "classpath:/META-INF/resources/webjars/jquery/3.6.0/", //
                "classpath:/slimselectjs/1.20.0/");
    }
}
