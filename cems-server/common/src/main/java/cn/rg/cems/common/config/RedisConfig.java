package cn.rg.cems.common.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Configuration
public class RedisConfig {

    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setValueSerializer(fastJsonRedisSerializer());
        template.setKeySerializer(fastJsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public FastJsonRedisSerializer<?> fastJsonRedisSerializer() {
        return new FastJsonRedisSerializer<>(Object.class);
    }
}
