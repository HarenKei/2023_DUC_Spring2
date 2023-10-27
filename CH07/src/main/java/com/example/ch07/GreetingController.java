package com.example.ch07;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GreetingController {
//    @Value("${greeting-name: test}")
//    private String name;
//
//    @Value("${greeting-coffee: ${greeting-name} likes CafeLatte}")
//    private String coffee;

    private final Greeting greeting;

    @GetMapping("/greeting")
    public String getGreeting() {
        return greeting.getName();
    }

    @GetMapping("/coffee")
    public String getNameAndCoffee() {
        return greeting.getCoffee();
    }

    @GetMapping("/droid")
    public Droid getDroid() {
        log.trace("트레이스 로그");
        log.info("인포 로그;");
        log.error("에러 로그");
        return new Droid();
    }
}
