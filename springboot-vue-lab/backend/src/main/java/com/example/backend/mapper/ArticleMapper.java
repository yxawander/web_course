package com.example.backend.mapper;

import com.example.backend.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    List<Article> findPage(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size);

    List<Article> findRecent(@Param("size") int size);

    long count(@Param("keyword") String keyword);

    long countAll();

    int insert(Article article);

    int update(Article article);

    int deleteById(@Param("id") Long id);
}
