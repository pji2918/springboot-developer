package com.pji2918.springdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

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
        person.setBlogId(1L);
        person.setHobbies(List.of("운동", "독서", "영화감상", "음악감상"));

        model.addAttribute(person);
        model.addAttribute("today", LocalDateTime.now());
        return "example";
    }
}

@Getter
@Setter
class Person {
    private String name;
    private int age;
    private Long blogId;
    private List<String> hobbies;
}