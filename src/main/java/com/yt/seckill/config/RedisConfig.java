package com.yt.seckill.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 应涛
 * @date 2021/11/21
 * @function：redis配置类，实现序列化
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //对redis进行相应的配置,使得redis存储时可以直观显示
        //key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //hash类型key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash类型value序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //注入连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return  redisTemplate;
    }

    @Bean(name = "IpCountRedisTemplate")
    RedisTemplate<String,Object> IpCountRedisTemplate(){
        RedisStandaloneConfiguration server = new RedisStandaloneConfiguration();
        server.setHostName("150.158.28.238"); // 指定地址
        server.setDatabase(0); // 指定数据库
        server.setPort(6370); //指定端口
        LettuceConnectionFactory factory = new LettuceConnectionFactory(server);
        factory.afterPropertiesSet(); //刷新配置

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        return redisTemplate;
    }
}
