package com.pji2918.springdeveloper.service;

import com.pji2918.springdeveloper.dao.Article;
import com.pji2918.springdeveloper.dto.ArticleCreationRequest;
import com.pji2918.springdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(ArticleCreationRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }
}
