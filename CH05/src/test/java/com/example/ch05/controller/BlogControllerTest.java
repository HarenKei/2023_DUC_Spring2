package com.example.ch05.controller;

import com.example.ch05.domain.Article;
import com.example.ch05.dto.AddArticleRequest;
import com.example.ch05.repository.BlogRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BlogRepository blogRepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("addArticle : 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        //given, when, then

        //given
        final String url = "/api/articles";
        final String title = "제목입니다.";
        final String content = "내용입니다.";
        final AddArticleRequest request = new AddArticleRequest(title, content);

        //객체를 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(request);

        //when 실행절
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then 검증절
        result.andExpect(status().isCreated());

        List<Article> articleList = blogRepository.findAll();
        assertThat(articleList.size()).isEqualTo(1);
        assertThat(articleList.get(0).getTitle()).isEqualTo(title);
        assertThat(articleList.get(0).getContent()).isEqualTo(content);
    }
}
