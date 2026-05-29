package com.example.backend.controller;

import com.example.backend.common.ApiResponse;
import com.example.backend.common.PageResult;
import com.example.backend.dto.ArticleRequest;
import com.example.backend.entity.Article;
import com.example.backend.service.ArticleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ApiResponse<PageResult<Article>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size) {
        return ApiResponse.ok(articleService.findPage(keyword, page, size));
    }

    @PostMapping
    public ApiResponse<Void> create(@RequestBody ArticleRequest request) {
        try {
            articleService.create(request);
            return ApiResponse.ok("文章创建成功", null);
        } catch (IllegalArgumentException ex) {
            return ApiResponse.fail(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody ArticleRequest request) {
        try {
            articleService.update(id, request);
            return ApiResponse.ok("文章更新成功", null);
        } catch (IllegalArgumentException ex) {
            return ApiResponse.fail(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ApiResponse.ok("文章删除成功", null);
    }
}
