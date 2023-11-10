package com.example.ch09;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class Ch09Application {

    public static void main(String[] args) {
        SpringApplication.run(Ch09Application.class, args);
    }

}
