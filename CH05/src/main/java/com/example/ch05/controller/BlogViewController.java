package com.example.ch05.controller;

import com.example.ch05.domain.Article;
import com.example.ch05.dto.ArticleListViewResponse;
import com.example.ch05.dto.ArticleResponse;
import com.example.ch05.dto.ArticleViewResponse;
import com.example.ch05.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping("/articles")
    public String findAllArticles(Model model) {
        List<ArticleListViewResponse> articleListViewResponses = blogService.findAll().stream().map(ArticleListViewResponse::new).toList();
        model.addAttribute("articles", articleListViewResponses);
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if(id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", article);
        }
        return "newArticle";
    }

}
