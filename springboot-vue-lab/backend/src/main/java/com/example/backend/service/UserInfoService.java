package com.example.backend.service;

import com.example.backend.common.PageResult;
import com.example.backend.entity.UserInfo;
import com.example.backend.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    private final UserInfoMapper userInfoMapper;

    public UserInfoService(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    public PageResult<UserInfo> findPage(String keyword, int page, int size) {
        int safePage = Math.max(page, 1);
        int safeSize = Math.min(Math.max(size, 1), 50);
        int offset = (safePage - 1) * safeSize;
        return new PageResult<>(userInfoMapper.count(keyword), userInfoMapper.findPage(keyword, offset, safeSize));
    }
}
