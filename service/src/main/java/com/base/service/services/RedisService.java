package com.base.service.services;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Redis 操作类
 */
public interface RedisService {

    int USER_TOKEN_EXPIRES = 86400;// token一天内有效


    /**
     * 设置key value
     *
     * @param key
     * @param value
     */
    boolean set(String key, Object value);

    /**
     * 设置key value 以及过期时间
     *
     * @param key
     * @param value
     * @param time
     */
    boolean set(String key, Object value, long time);

    /**
     * 获取value
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 判断是否存在key
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 删除key
     * @param key
     * @return
     */
    boolean delete(String key);

    /**
     * 获取key的过期时间
     * @param key
     * @return
     */
    long getExpireTime(String key);


    // list
    long lpush(String key, String value);

    String rpop(String key);

    long size(String key);

    List<Object> range(String key);

    Object index(String key, int index);

    Long remove(String key, int count, Object value);

    // hset
    void put(String key, String hashKey, String value);

    void putAll(String key, Map<String, Object> map);

    Map<String, Object> entries(String key);

    Set<Object> keys(String key);

    String get(String key, String hashKey);



}
