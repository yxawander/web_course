package com.example.backend.mapper;

import com.example.backend.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
    List<UserInfo> findPage(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size);

    long count(@Param("keyword") String keyword);
}
