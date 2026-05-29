package com.example.backend.controller;

import com.example.backend.common.ApiResponse;
import com.example.backend.common.PageResult;
import com.example.backend.entity.UserInfo;
import com.example.backend.service.UserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserInfoController {
    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping
    public ApiResponse<PageResult<UserInfo>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size) {
        return ApiResponse.ok(userInfoService.findPage(keyword, page, size));
    }
}
