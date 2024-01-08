

package com.xpresspayment.takehometest.common.configs.cache;

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
