package com.wangzi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="spring.redis.cluster")
public class ClusterRedisPoolProperties {
	private int maxIdel;
	private long maxWaitMillis;
	private int maxTotal;
	private long minEvictableIdleTimeMillis;
	private int minIdel;
	private boolean lifo;
	private long softMinEvictableIdleTimeMillis;
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
	@Override
	public String toString() {
		return "ClusterRedisPoolProperties [maxIdel=" + maxIdel + ", maxWaitMillis=" + maxWaitMillis + ", maxTotal="
				+ maxTotal + ", minEvictableIdleTimeMillis=" + minEvictableIdleTimeMillis + ", minIdel=" + minIdel
				+ ", lifo=" + lifo + ", softMinEvictableIdleTimeMillis=" + softMinEvictableIdleTimeMillis + "]";
	}
	
}
