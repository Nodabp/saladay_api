package com.saladay.saladay_api.util.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

        @Value("${com.saladay.cors.url}")
        private String url;

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**") // 모든 경로
                    .allowedOrigins(url) // 요청 허용할 주소
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowCredentials(true); // 쿠키 포함 시 true
        }
}
