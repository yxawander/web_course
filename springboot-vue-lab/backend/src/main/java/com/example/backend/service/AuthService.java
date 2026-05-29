package com.example.backend.service;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.PasswordRequest;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.entity.LoginUser;
import com.example.backend.mapper.LoginUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Service
public class AuthService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    private final LoginUserMapper loginUserMapper;

    public AuthService(LoginUserMapper loginUserMapper) {
        this.loginUserMapper = loginUserMapper;
    }

    public LoginUser login(LoginRequest request) {
        if (!StringUtils.hasText(request.getUsername()) || !StringUtils.hasText(request.getPassword())) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }
        LoginUser user = loginUserMapper.findByUsername(request.getUsername());
        if (user == null || !request.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("用户名或密码错误");
        }
        user.setPassword(null);
        return user;
    }

    public void register(RegisterRequest request) {
        if (!StringUtils.hasText(request.getUsername()) || !StringUtils.hasText(request.getPassword())) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }
        if (!StringUtils.hasText(request.getEmail()) || !EMAIL_PATTERN.matcher(request.getEmail()).matches()) {
            throw new IllegalArgumentException("邮箱格式不正确");
        }
        if (request.getBirthday() == null) {
            throw new IllegalArgumentException("出生日期不能为空");
        }
        if (loginUserMapper.findByUsername(request.getUsername()) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        LoginUser user = new LoginUser();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setBirthday(request.getBirthday());
        user.setNickname(StringUtils.hasText(request.getNickname()) ? request.getNickname() : request.getUsername());
        loginUserMapper.insert(user);
    }

    public void changePassword(PasswordRequest request) {
        if (!StringUtils.hasText(request.getUsername())) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (!StringUtils.hasText(request.getNewPassword()) || !request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("两次输入的新密码不一致");
        }
        if (loginUserMapper.findByUsername(request.getUsername()) == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        loginUserMapper.updatePassword(request.getUsername(), request.getNewPassword());
    }
}
