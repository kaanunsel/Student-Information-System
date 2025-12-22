package org.pangea.sis.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis configuration class for caching.
 * Enables caching and configures custom TTLs for different cache regions.
 * Falls back to in-memory caching if Redis is not available.
 */
@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * Configures the RedisCacheManager with custom TTL settings per cache.
     * Only enabled when Redis is available (spring.cache.type=redis).
     *
     * @param connectionFactory Redis connection factory
     * @return configured CacheManager
     */
    @Bean
    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        // Configure ObjectMapper to handle Java 8 date/time types
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // Default cache configuration with JSON serialization
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer(objectMapper)
                        )
                );

        // Custom TTL configurations for specific caches
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        // High-frequency read caches with longer TTL (10 minutes)
        cacheConfigurations.put("courses", defaultConfig.entryTtl(Duration.ofMinutes(10)));
        cacheConfigurations.put("students", defaultConfig.entryTtl(Duration.ofMinutes(10)));
        cacheConfigurations.put("instructors", defaultConfig.entryTtl(Duration.ofMinutes(10)));
        
        // Individual entity caches (5 minutes)
        cacheConfigurations.put("course", defaultConfig.entryTtl(Duration.ofMinutes(5)));
        cacheConfigurations.put("student", defaultConfig.entryTtl(Duration.ofMinutes(5)));
        cacheConfigurations.put("instructor", defaultConfig.entryTtl(Duration.ofMinutes(5)));
        
        // Lookup caches (5 minutes)
        cacheConfigurations.put("coursesByInstructor", defaultConfig.entryTtl(Duration.ofMinutes(5)));
        
        // Analytics cache with shorter TTL (3 minutes) since it changes with enrollments
        cacheConfigurations.put("analytics", defaultConfig.entryTtl(Duration.ofMinutes(3)));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    /**
     * Fallback in-memory cache manager when Redis is not available.
     * This prevents the application from crashing when Redis is not running.
     *
     * @return simple in-memory CacheManager
     */
    @Bean
    @Primary
    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "simple", matchIfMissing = true)
    public CacheManager simpleCacheManager() {
        return new ConcurrentMapCacheManager(
                "courses", "students", "instructors",
                "course", "student", "instructor",
                "coursesByInstructor", "analytics"
        );
    }
}
