package com.shop.quick.redisTestConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

public class RedisConfig {
    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = null;

        try {
            RedisStandaloneConfiguration conn = new RedisStandaloneConfiguration();
            conn.setHostName(host);
            conn.setPort(port);

            jedisConnectionFactory = new JedisConnectionFactory(conn);

        } catch (Exception e) {
        }

        return jedisConnectionFactory;
    }

    @Bean(name="redisTemplate")
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Object.class));

        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        return redisTemplate;
    }

}
