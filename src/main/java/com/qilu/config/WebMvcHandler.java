package com.qilu.config;


import com.qilu.intercepter.LoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcHandler implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        List<String> addPatterns=new ArrayList<>();
//        addPatterns.add("/admin/api/**");
//        addPatterns.add("/*.html");
//        List<String> excludePatterns=new ArrayList<>();
//        excludePatterns.add("/admin/api/login");
//        excludePatterns.add("/login.html");
//        registry.addInterceptor(new LoginIntercepter()).
//                addPathPatterns(addPatterns).
//                excludePathPatterns(excludePatterns);
//
//        WebMvcConfigurer.super.addInterceptors(registry);
//
//    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }
}
