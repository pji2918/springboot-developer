package com.pji2918.springdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ArticleUpdateRequest {
    private String title;
    private String content;
}
