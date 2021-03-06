package org.bapp.config;

import org.bapp.web.interceptor.LoggerInterceptor;
import org.bapp.web.interceptor.SessionTimerInterceptor;
import org.bapp.web.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    public WebConfig(){
        super();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/assets/img/**",
                "/assets/css/**",
                "/assets/js/**",
                "/printtemp/**")
                .addResourceLocations(
                        "/webjars/",
                        "classpath:/static/assets/img/",
                        "classpath:/static/assets/css/",
                        "classpath:/static/assets/js/",
                        "classpath:/static/printtemp/");
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor());
        registry.addInterceptor(new UserInterceptor());
        registry.addInterceptor(new SessionTimerInterceptor());
    }
}
