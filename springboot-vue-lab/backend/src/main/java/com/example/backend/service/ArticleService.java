package com.example.backend.service;

import com.example.backend.common.PageResult;
import com.example.backend.dto.ArticleRequest;
import com.example.backend.entity.Article;
import com.example.backend.mapper.ArticleMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ArticleService {
    private final ArticleMapper articleMapper;

    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public PageResult<Article> findPage(String keyword, int page, int size) {
        int safePage = Math.max(page, 1);
        int safeSize = Math.min(Math.max(size, 1), 50);
        int offset = (safePage - 1) * safeSize;
        return new PageResult<>(articleMapper.count(keyword), articleMapper.findPage(keyword, offset, safeSize));
    }

    public void create(ArticleRequest request) {
        Article article = toArticle(request);
        articleMapper.insert(article);
    }

    public void update(Long id, ArticleRequest request) {
        Article article = toArticle(request);
        article.setId(id);
        articleMapper.update(article);
    }

    public void delete(Long id) {
        articleMapper.deleteById(id);
    }

    private Article toArticle(ArticleRequest request) {
        if (!StringUtils.hasText(request.getTitle())) {
            throw new IllegalArgumentException("文章标题不能为空");
        }
        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setCategory(StringUtils.hasText(request.getCategory()) ? request.getCategory() : "通知");
        article.setAuthor(StringUtils.hasText(request.getAuthor()) ? request.getAuthor() : "管理员");
        article.setSummary(StringUtils.hasText(request.getSummary()) ? request.getSummary() : "暂无摘要");
        article.setStatus(StringUtils.hasText(request.getStatus()) ? request.getStatus() : "草稿");
        return article;
    }
}
