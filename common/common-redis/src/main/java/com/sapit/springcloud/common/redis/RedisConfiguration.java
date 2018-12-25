package com.sapit.springcloud.common.redis;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {
	
	@Bean(name = "jedis.pool.config")
	public JedisPoolConfig jedisPoolConfig(@Value("${spring.redis.jedis.pool.max-active}") int maxTotal,
			@Value("${spring.redis.jedis.pool.max-idle}") int maxIdle,
			@Value("${spring.redis.jedis.pool.max-wait}") int maxWaitMillis) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		return config;
	}

	@Bean(name = "jedis.pool")
	@Autowired
	public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config,
			@Value("${spring.redis.host}") String host, @Value("${spring.redis.port}") int port) {
		return new JedisPool(config, host, port);
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		// user信息缓存配置
		RedisCacheConfiguration userCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(10)).disableCachingNullValues().prefixKeysWith("user");
		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
		redisCacheConfigurationMap.put("user", userCacheConfiguration);
		// 初始化一个RedisCacheWriter
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();

		// 设置默认超过期时间是30秒
		defaultCacheConfig.entryTtl(Duration.ofSeconds(30));
		// 初始化RedisCacheManager
		RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig, redisCacheConfigurationMap);
		return cacheManager;
	}

}