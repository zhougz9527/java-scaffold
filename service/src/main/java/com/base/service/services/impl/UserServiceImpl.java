package com.base.service.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.service.entity.User;
import com.base.service.mapper.UserMapper;
import com.base.service.services.RedisService;
import com.base.service.services.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisService redisService;

    public User singleTableQuery(String name) {
        return userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getName, name));
    }

    public Page<User> singleTableQueryByPaging(Long classGradeId) {
        return null;
    }

    public List<Map<String, Object>> multiTableQuery(Long id) {
        return null;
    }

    public Page<Map<String, Object>> multiTableQueryByPaging(Long classGradeId) {
        return null;
    }

    @Override
    public User getUserInfoByRedis(String token) {
        return (User) redisService.get(token);
    }
}
