package com.saladay.saladay_api.util.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**") // 모든 경로
                    .allowedOrigins("http://localhost:5173") // 요청 허용할 주소
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowCredentials(true); // 쿠키 포함 시 true
        }
    //@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") 특정만 열고 싶을때는 컨트롤러에 이렇게 매핑.
}
