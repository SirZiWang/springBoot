package com.wangzi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class AppConfig {
	@Autowired
	ClusterConfigurationProperties clusterProperties;
	
	@Autowired
	ClusterRedisPoolProperties redisPool;
	
	@Bean
	public RedisConnectionFactory connectionFactory(){
		JedisConnectionFactory factory = new JedisConnectionFactory(new RedisClusterConfiguration(clusterProperties.getNodes()));
		JedisPoolConfig config = factory.getPoolConfig();
		config.setMaxIdle(redisPool.getMaxIdel());
		config.setMaxWaitMillis(redisPool.getMaxWaitMillis());
    	config.setMaxTotal(redisPool.getMaxTotal());
    	config.setMinEvictableIdleTimeMillis(redisPool.getMinEvictableIdleTimeMillis());
    	config.setMinIdle(redisPool.getMinIdel());
    	config.setLifo(redisPool.isLifo());
    	config.setSoftMinEvictableIdleTimeMillis(redisPool.getSoftMinEvictableIdleTimeMillis());
    	return factory;
	}
}
