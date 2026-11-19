package com.pji2918.springdeveloper.controller;

import com.pji2918.springdeveloper.dao.Article;
import com.pji2918.springdeveloper.dto.ArticleCreationRequest;
import com.pji2918.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    // given
    private final String TITLE = "test title";
    private final String CONTENT = "test content";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void cleanUp() {
        blogRepository.deleteAll();
    }

    @Test
    @DisplayName("addArticle: 글 추가에 성공한다.")
    void addArticle() throws Exception {
        // Given
        ArticleCreationRequest request = new ArticleCreationRequest(TITLE, CONTENT);

        // When
        // 글 등록
        mockMvc.perform(post("/api/articles").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                // Then
                // 등록에 성공했는가?
                .andExpect(status().isCreated())
                // 제목과 내용이 일치하는가?
                .andExpect(jsonPath("$.title").value(TITLE))
                .andExpect(jsonPath("$.content").value(CONTENT));

        // Then 2
        // DB의 값이 일치하는가?
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.getFirst().getTitle()).isEqualTo(TITLE);
        assertThat(articles.getFirst().getContent()).isEqualTo(CONTENT);
    }

    @Test
    @DisplayName("getAllArticles: 모든 블로그 글 조회에 성공한다.")
    void getAllArticles() throws Exception {
        // Given
        blogRepository.save(new Article(TITLE, CONTENT));

        // When
        ResultActions result = mockMvc.perform(get("/api/articles").accept(MediaType.APPLICATION_JSON));

        // Then
        // 반환된 값이 배열인가?
        result.andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
                // 조회된 글이 일치하는가?
                .andExpect(jsonPath("$[0].title").value(TITLE))
                .andExpect(jsonPath("$[0].content").value(CONTENT));
    }

    @Test
    @DisplayName("getArticle: 특정 블로그 글 조회에 성공한다.")
    void getArticle() throws Exception {
        // Given
        Article article = blogRepository.save(new Article(TITLE, CONTENT));

        // When
        mockMvc.perform(get("/api/articles/{id}", article.getId()))
                // Then
                // 조회된 글이 일치하는가?
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(TITLE))
                .andExpect(jsonPath("$.content").value(CONTENT));
    }

    @Test
    @DisplayName("deleteArticle: 특정 블로그 글 삭제에 성공한다.")
    void deleteArticle() throws Exception {
        // Given
        Article article = blogRepository.save(new Article(TITLE, CONTENT));
        Article article2 = blogRepository.save(new Article(TITLE, CONTENT));

        // When
        // 삭제에 성공하는가?
        mockMvc.perform(delete("/api/articles/{id}", article.getId()))
                // Then
                .andExpect(status().isNoContent());
        // 삭제된 글 조회 시 404인가?
        mockMvc.perform(get("/api/articles/{id}", article.getId()))
                // Then
                .andExpect(status().isNotFound());
        // 다른 글이 삭제되지는 않았는가?
        mockMvc.perform(get("/api/articles/{id}", article2.getId()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(TITLE))
                .andExpect(jsonPath("$.content").value(CONTENT));
    }

    @Test
    @DisplayName("updateArticle: 특정 블로그 글 수정에 성공한다.")
    void updateArticle() throws Exception {
        // Given
        Article article = blogRepository.save(new Article(TITLE, CONTENT));
        Article article2 = blogRepository.save(new Article(TITLE, CONTENT));

        // When
        mockMvc.perform(put("/api/articles/{id}", article.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new Article(TITLE + "2", CONTENT + "2"))))
                // Then
                // 수정에 성공했는가?
                .andExpect(status().isOk())
                // 수정된 글이 일치하는가?
                .andExpect(jsonPath("$.title").value(TITLE + "2"))
                .andExpect(jsonPath("$.content").value(CONTENT + "2"));

        // When 2
        // 다른 글이 수정되지는 않았는가?
        mockMvc.perform(get("/api/articles/{id}", article2.getId()))
                // Then 2
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(TITLE))
                .andExpect(jsonPath("$.content").value(CONTENT));
    }
}