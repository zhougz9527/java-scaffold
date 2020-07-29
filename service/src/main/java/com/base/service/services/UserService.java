package com.base.service.services;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.service.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    // 单表查询
    User singleTableQuery(String name);

    // 单表分页查询
    Page<User> singleTableQueryByPaging(Long classGradeId);

    // 多表查询
    List<Map<String, Object>> multiTableQuery(Long id);

    // 多表分页查询
    Page<Map<String, Object>> multiTableQueryByPaging(Long classGradeId);

    // 获取redis中的用户的信息
    User getUserInfoByRedis(String token);

}
