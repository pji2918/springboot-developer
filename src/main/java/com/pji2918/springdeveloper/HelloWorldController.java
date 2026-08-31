package com.pji2918.springdeveloper;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    // 클라이언트가 "/hello"로 요청을 보내면 해당 메서드 호출
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
