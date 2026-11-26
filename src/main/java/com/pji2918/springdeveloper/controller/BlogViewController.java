package com.pji2918.springdeveloper.controller;

import com.pji2918.springdeveloper.dao.Article;
import com.pji2918.springdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping("/articles")
    public String blog(Model model) {
        List<Article> articles = blogService.findAll();

        model.addAttribute("articles", articles);
        return "articleList";
    }
}
