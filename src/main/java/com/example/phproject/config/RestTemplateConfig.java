package com.example.phproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){ //RestTemplate은 HTTP 통신을 단순화하고 RESTful 웹 서비스에 대한 요청을 쉽게 만들 수 있는 Spring의 클라이언트 측 HTTP 통신 라이브러리
        return new RestTemplate();
    }

}