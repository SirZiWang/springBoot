package com.wangzi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisClusterConfig {
	@Value("${spring.cluster.pool.maxIdel}")
	private int maxIdel;
	@Value("${spring.cluster.pool.maxWaitMillis}")
	private long maxWaitMillis;
	@Value("${spring.cluster.pool.maxTotal}")
	private int maxTotal;
	@Value("${spring.cluster.pool.minEvictableIdleTimeMillis}")
	private long minEvictableIdleTimeMillis;
	@Value("${spring.cluster.pool.minIdel}")
	private int minIdel;
	@Value("${spring.cluster.pool.lifo}")
	private boolean lifo;
	@Value("${spring.cluster.pool.softMinEvictableIdleTimeMillis}")
	private long softMinEvictableIdleTimeMillis;
	@Value("${spring.redis.cluster.nodes}")
	private String nodes;
	@Value("${spring.redis.cluster.max-redirects}")
	private int maxRedirects;
	
	public int getMaxIdel() {
		return maxIdel;
	}
	public void setMaxIdel(int maxIdel) {
		this.maxIdel = maxIdel;
	}
	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}
	public void setMaxWaitMillis(long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
	public int getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}
	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}
	public int getMinIdel() {
		return minIdel;
	}
	public void setMinIdel(int minIdel) {
		this.minIdel = minIdel;
	}
	public boolean isLifo() {
		return lifo;
	}
	public void setLifo(boolean lifo) {
		this.lifo = lifo;
	}
	public long getSoftMinEvictableIdleTimeMillis() {
		return softMinEvictableIdleTimeMillis;
	}
	public void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis) {
		this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
	}
	public String getNodes() {
		return nodes;
	}
	public void setNodes(String nodes) {
		this.nodes = nodes;
	}
	public int getMaxRedirects() {
		return maxRedirects;
	}
	public void setMaxRedirects(int maxRedirects) {
		this.maxRedirects = maxRedirects;
	}
}
