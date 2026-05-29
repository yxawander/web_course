package com.example.backend.controller;

import com.example.backend.common.ApiResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.PasswordRequest;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.entity.LoginUser;
import com.example.backend.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginUser> login(@RequestBody LoginRequest request) {
        try {
            return ApiResponse.ok("登录成功", authService.login(request));
        } catch (IllegalArgumentException ex) {
            return ApiResponse.fail(ex.getMessage());
        }
    }

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ApiResponse.ok("注册成功", null);
        } catch (IllegalArgumentException ex) {
            return ApiResponse.fail(ex.getMessage());
        }
    }

    @PostMapping("/password")
    public ApiResponse<Void> changePassword(@RequestBody PasswordRequest request) {
        try {
            authService.changePassword(request);
            return ApiResponse.ok("密码修改成功", null);
        } catch (IllegalArgumentException ex) {
            return ApiResponse.fail(ex.getMessage());
        }
    }
}
