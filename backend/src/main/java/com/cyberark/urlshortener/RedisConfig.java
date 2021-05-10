package com.cyberark.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
 
@Configuration
public class RedisConfig {
 
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
 
    // Set up redis template of String id (hashed url id) to String url. 
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
 
        final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        
        return redisTemplate;
    }
}