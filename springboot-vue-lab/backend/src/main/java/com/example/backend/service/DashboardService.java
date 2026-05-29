package com.example.backend.service;

import com.example.backend.entity.Article;
import com.example.backend.mapper.ArticleMapper;
import com.example.backend.mapper.LoginUserMapper;
import com.example.backend.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    private final LoginUserMapper loginUserMapper;
    private final UserInfoMapper userInfoMapper;
    private final ArticleMapper articleMapper;

    public DashboardService(LoginUserMapper loginUserMapper, UserInfoMapper userInfoMapper, ArticleMapper articleMapper) {
        this.loginUserMapper = loginUserMapper;
        this.userInfoMapper = userInfoMapper;
        this.articleMapper = articleMapper;
    }

    public Map<String, Object> summary() {
        Map<String, Object> data = new HashMap<>();
        data.put("loginUserCount", loginUserMapper.count());
        data.put("userInfoCount", userInfoMapper.count(null));
        data.put("articleCount", articleMapper.countAll());
        data.put("pendingTasks", 6);
        List<Article> recentArticles = articleMapper.findRecent(5);
        data.put("recentArticles", recentArticles);
        return data;
    }
}
