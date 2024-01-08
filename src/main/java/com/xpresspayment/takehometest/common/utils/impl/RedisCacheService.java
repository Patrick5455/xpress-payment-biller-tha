/*
 * Copyright (c) 2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */

package com.xpresspayment.takehometest.commons.utils.impl;

import com.google.gson.Gson;
import com.xpresspayment.takehometest.commons.exceptions.AppException;
import com.xpresspayment.takehometest.commons.utils.i.CachingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
@Qualifier(value = "redis")
@Primary
@Component
public class RedisCacheService extends CachingService<String, Object> {

    private final JedisPool jedisPool;

    public RedisCacheService(JedisPool jedisPool){
        this.jedisPool = jedisPool;
    }

    @Override
    public Object getOrDefault(String key, Object defaultValue) {
        return getValue(key);
    }

    @Override
    public Object getOrDefault(String key, Object defaultValue, Class<?> deserializeTo) {
        String serializedObject = getValue(key);
        return new Gson().fromJson(serializedObject, deserializeTo);
    }

    @Override
    public void saveOrUpdate(String key, Object value) {
        if (!(value instanceof String)) {
            setValue(key, new Gson().toJson(value));
            return;
        }
        setValue(key, (String) value);
    }

    @Override
    public void saveOrUpdate(String key, Object value, Class<?> serializeFrom) throws AppException {
        throw new AppException("no cache implementation found");
    }

    @Override
    public void remove(String key) {removeValue(key);}

    @Override
    public long expireKey (String key, int seconds) {
        return expire(key, seconds);
    }

    @Override
    public boolean contains(String key) {
        return isKeyExist(key);
    }


    private void setValue(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    private void removeValue(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }


    private String getValue(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    private boolean isKeyExist(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String val =  jedis.get(key);
          return !(val == null || val.isEmpty());
        }
    }

    private Long expire(String key, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.expire(key, seconds);
        }
    }
}
