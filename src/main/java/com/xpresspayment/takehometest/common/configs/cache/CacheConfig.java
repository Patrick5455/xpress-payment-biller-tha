/*
 * Copyright (c) 2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */

package com.xpresspayment.takehometest.commons.configs.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Slf4j
public class CacheConfig {


    @Profile({"!unittest", "!i-test"})
    @ConfigurationProperties(prefix = "cache")
    private CacheProperties cacheProperties(){
        return CacheProperties.builder()
                .build();
    }

    @Bean
    public JedisPool jedisPool() {
        log.info("cache host: {}", cacheProperties().getHost());
        return new JedisPool(new JedisPoolConfig(), cacheProperties().getHost());
    }
}
