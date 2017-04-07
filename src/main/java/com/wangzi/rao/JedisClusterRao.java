package com.wangzi.rao;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wangzi.config.RedisClusterConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisClusterRao {
	@Autowired
	RedisClusterConfig jedisClusterConfig;
	
	@Bean
	public JedisCluster jedisCluster() {
		String[] serverArray = jedisClusterConfig.getNodes().split(",");// 获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
		Set<HostAndPort> nodes = new HashSet<>();
		for (String ipPort : serverArray) {
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
		}
		JedisPoolConfig JedisPoolConfig = new JedisPoolConfig();
		return new JedisCluster(nodes,JedisPoolConfig);
	}
}
