package com.example.phproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //auditing 활성화
@SpringBootApplication
public class PHprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PHprojectApplication.class, args);
    }

}
