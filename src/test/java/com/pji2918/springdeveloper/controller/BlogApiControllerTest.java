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
import java.util.Optional;

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
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.content").value(request.getContent()));

        // Then 2
        // DB의 값이 일치하는가?
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.getFirst().getTitle()).isEqualTo(request.getTitle());
        assertThat(articles.getFirst().getContent()).isEqualTo(request.getContent());
    }

    @Test
    @DisplayName("findArticle: 특정 블로그 글 조회에 성공한다.")
    void findArticle() throws Exception {
        // Given
        Article article = blogRepository.save(new Article(TITLE, CONTENT));

        // When
        mockMvc.perform(get("/api/articles/{id}", article.getId()))
                // Then
                // 응답이 200인가?
                .andExpect(status().isOk())
                // 조회된 글이 일치하는가?
                .andExpect(jsonPath("$.title").value(article.getTitle()))
                .andExpect(jsonPath("$.content").value(article.getContent()));
    }


    @Test
    @DisplayName("updateArticle: 특정 블로그 글 수정에 성공한다.")
    void updateArticle() throws Exception {
        // Given
        Article article = blogRepository.save(new Article(TITLE, CONTENT));

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

        // Then 2: DB 값 비교
        Optional<Article> targetArticle = blogRepository.findById(article.getId());
        assertThat(targetArticle).isPresent();
        assertThat(targetArticle.get().getTitle()).isEqualTo(article.getTitle() + "2");
        assertThat(targetArticle.get().getContent()).isEqualTo(article.getContent() + "2");
    }


    @Test
    @DisplayName("deleteArticle: 특정 블로그 글 삭제에 성공한다.")
    void deleteArticle() throws Exception {
        // Given
        Article article = blogRepository.save(new Article(TITLE, CONTENT));

        // When
        // 삭제에 성공하는가?
        mockMvc.perform(delete("/api/articles/{id}", article.getId()))
                // Then 1
                .andExpect(status().isNoContent());
        // 삭제된 글 조회 시 404인가?
        mockMvc.perform(get("/api/articles/{id}", article.getId()))
                // Then 2
                .andExpect(status().isNotFound());
        // Then 3
        // blogRepository가 비어 있는가?
        assertThat(blogRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("findAllArticles: 모든 블로그 글 조회에 성공한다.")
    void findAllArticles() throws Exception {
        // Given
        Article article = blogRepository.save(new Article(TITLE, CONTENT));
        Article article2 = blogRepository.save(new Article(TITLE + "1", CONTENT + "1"));

        // When
        ResultActions result = mockMvc.perform(get("/api/articles").accept(MediaType.APPLICATION_JSON));

        // Then
        // 응답이 200인가?
        result.andExpect(status().isOk())
                // 반환된 값이 배열인가?
                .andExpect(jsonPath("$").isArray())
                // 길이가 2인가?
                .andExpect(jsonPath("$.length()").value(2))
                // 조회된 글이 일치하는가?
                .andExpect(jsonPath("$[0].title").value(article.getTitle()))
                .andExpect(jsonPath("$[0].content").value(article.getContent()))
                // 조회된 2번째 글이 일치하는가?
                .andExpect(jsonPath("$[1].title").value(article2.getTitle()))
                .andExpect(jsonPath("$[1].content").value(article2.getContent()));
    }

}
