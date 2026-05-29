package com.example.backend.mapper;

import com.example.backend.entity.LoginUser;
import org.apache.ibatis.annotations.Param;

public interface LoginUserMapper {
    LoginUser findByUsername(@Param("username") String username);

    int insert(LoginUser user);

    int updatePassword(@Param("username") String username, @Param("password") String password);

    long count();
}
