package com.example.webapplication;

import com.example.webapplication.model.service.impl.CurrencyServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args)  {
        SpringApplication.run(WebApplication.class, args);
    }

}
