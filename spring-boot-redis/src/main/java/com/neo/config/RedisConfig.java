package com.neo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


/**
 * @author rainlin
 */
@Configuration
@EnableCaching
@ConfigurationProperties("spring.cache.redis")
public class RedisConfig extends CachingConfigurerSupport {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final RedisConnectionFactory redisConnectionFactory;
    private Map<String, Long> ttl;

    public RedisConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 重写CacheManager，通过配置spring.cache.redis.ttl支持自定义每个cacheName的过期时间
     *
     */
    @Override
    @Bean
    public CacheManager cacheManager() {
        Map<String, RedisCacheConfiguration> cachingConfigurerMap = new HashMap<>(ttl.size());
        ttl.forEach((key, value) -> cachingConfigurerMap.put(key, getCacheConfigurationWithTtl(value)));
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                //.cacheDefaults(redisCacheConfiguration())
                .cacheDefaults(getCacheConfigurationWithTtl(60))
                .withInitialCacheConfigurations(cachingConfigurerMap)
                .build();
    }

    private RedisCacheConfiguration getCacheConfigurationWithTtl(long seconds) {
        return redisCacheConfiguration().entryTtl(Duration.ofSeconds(seconds));
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        // 使用Jackson2JsonRedisSerializer配置序列化
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // 设置时间格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_TIME_PATTERN));

        serializer.setObjectMapper(objectMapper);

        return RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
    }


    public Map<String, Long> getTtl() {
        return ttl;
    }

    public void setTtl(Map<String, Long> ttl) {
        this.ttl = ttl;
    }
}