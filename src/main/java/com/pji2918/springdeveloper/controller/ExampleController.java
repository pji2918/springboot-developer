package com.pji2918.springdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExampleController {
    @GetMapping("/thymeleaf/example")
    @ResponseBody
    public String thymeleaf() {
        return "example";
    }

    @GetMapping("/thymeleaf/example2")
    public String thymeleaf2(Model model) {
        Person person = new Person();
        person.setName("홍길동");
        person.setAge(23);

        model.addAttribute(person);
        return "example";
    }
}

@Getter
@Setter
class Person {
    private String name;
    private int age;
}