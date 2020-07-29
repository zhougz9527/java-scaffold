package com.base.service.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.base.service.services.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 基本操作类
 */
@Service
public class RedisServiceImpl extends BaseServiceImpl implements RedisService {

    @Resource
    private RedisTemplate redisTemplate;

    private boolean flag = false;

    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        this.redisTemplate = redisTemplate;
    }


    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("存入redis失败: {}", e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean set(String key, Object value, long time) {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("存入redis失败: {}", e.getMessage());
        }
        return flag;
    }

    @Override
    public Object get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean exists(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean delete(String key) {
        if (exists(key)) {
            return redisTemplate.delete(key);
        }
        return false;
    }

    @Override
    public long getExpireTime(String key) {
        if (StringUtils.isEmpty(key)) {
            return 0L;
        }
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    @Override
    public long lpush(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return 0L;
        }
        return redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public String rpop(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return (String)redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public long size(String key) {
        if (StringUtils.isEmpty(key)) {
            return 0L;
        }
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public List<Object> range(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    @Override
    public Object index(String key, int index) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForList().index(key, index);
    }

    @Override
    public Long remove(String key, int count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    @Override
    public void put(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public void putAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public Map<String, Object> entries(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Set<Object> keys(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForHash().keys(key);
    }

    @Override
    public String get(String key, String hashKey) {
        return (String) redisTemplate.opsForHash().get(key, hashKey);
    }
}
