package com.saladay.saladay_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SaladayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaladayApiApplication.class, args);
    }

}
