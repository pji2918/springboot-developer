package com.pji2918.springdeveloper.dto;

import com.pji2918.springdeveloper.dao.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleCreationRequest {
    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder().title(this.title).content(this.content).build();
    }
}
