package com.pji2918.springdeveloper.controller;

import com.pji2918.springdeveloper.dao.Article;
import com.pji2918.springdeveloper.dto.ArticleCreationRequest;
import com.pji2918.springdeveloper.dto.ArticleResponse;
import com.pji2918.springdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BlogApiController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleCreationRequest request) {
        log.info("Article creation request received: {}, {}", request.getTitle(), request.getContent());
        Article article = blogService.save(request);
        return ResponseEntity.created(URI.create("/api/articles/" + article.getId())).body(article);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getArticles() {
//        List<Article> articles = blogService.findAll();
//        List<ArticleResponse> articleResponseList = new ArrayList<>();
//        for (Article article : articles) {
//            articleResponseList.add(new ArticleResponse(article));
//        }
//        return ResponseEntity.ok(articleResponseList);
        return ResponseEntity.ok(blogService.findAll().parallelStream().map(ArticleResponse::new).toList());
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id) {
        Article article = blogService.findById(id);
        if (article == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new ArticleResponse(article));
        }
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        try {
            blogService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
