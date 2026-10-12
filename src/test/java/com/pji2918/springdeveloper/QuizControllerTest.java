package com.pji2918.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /quiz?code=1 => 201")
    void getQuiz1() throws Exception {
        mockMvc.perform(get("/quiz").param("code", "1"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Created!"));
    }

    @Test
    @DisplayName("GET /quiz?code=2 => 400")
    void getQuiz2() throws Exception {
        mockMvc.perform(get("/quiz").param("code", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad Request!"));
    }

    @Test
    @DisplayName("GET /quiz?code=3 => 200")
    void getQuiz3() throws Exception {
        mockMvc.perform(get("/quiz").param("code", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK!"));
    }

    @Test
    @DisplayName("POST /quiz, value: 1 => 403")
    void postQuiz1() throws Exception {
        mockMvc.perform(post("/quiz")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new Code(1))))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Forbidden!"));
    }

    @Test
    @DisplayName("POST /quiz, value: 2 => 200")
    void postQuiz2() throws Exception {
        mockMvc.perform(post("/quiz")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new Code(2))))
                .andExpect(status().isOk())
                .andExpect(content().string("OK!"));
    }
}