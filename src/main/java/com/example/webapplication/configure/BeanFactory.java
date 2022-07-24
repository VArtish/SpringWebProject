package com.example.webapplication.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class BeanFactory {
    private static final String CURRATE_URL = "https://currate.ru";

    @Bean(name = "currateRestTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(CURRATE_URL));
        return restTemplate;
    }
}
