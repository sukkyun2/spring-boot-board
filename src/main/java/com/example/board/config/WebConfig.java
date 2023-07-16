package com.example.board.config;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final CorsConfig corsConfig;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsConfig.getAllowOrigins())
                .allowedMethods(corsConfig.getAllowMethods());
    }

    @Bean
    public FilterRegistrationBean<BoardUserTokenFilter> boardUserTokenFilterFilterRegistrationBean(){
        FilterRegistrationBean<BoardUserTokenFilter> filterRegistrationBean = new FilterRegistrationBean<>(new BoardUserTokenFilter());
        filterRegistrationBean.addUrlPatterns("/api/*");
        return filterRegistrationBean;
    }

    @Component
    @ConfigurationProperties(prefix = "cors")
    @Data
    public static class CorsConfig {
        private String[] allowOrigins;
        private String[] allowMethods;
    }
}
