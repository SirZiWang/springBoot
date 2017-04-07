package com.wangzi.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public abstract class JedisSubscribe implements ISubscribe{

	public abstract void onMessage(String channel, String message);
	public abstract void onPMessage(String pattern, String channel, String message);
	public abstract void onSubscribe(String channel, int subscribedChannels);
	public abstract void onUnsubscribe(String channel, int subscribedChannels);
	public abstract void onPUnsubscribe(String pattern, int subscribedChannels);
	public abstract void onPSubscribe(String pattern, int subscribedChannels);
	
	public abstract Jedis getJedis();
	public abstract String getChannel();
	public JedisSubscribe(){}
	
	public void init(){
		final JedisSubscribe jedisSubscribe = this;
		final String channel = getChannel();
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				getJedis().subscribe(new JedisPubSub(){
					@Override
					public void onMessage(String channel, String message) {
						jedisSubscribe.onMessage(channel, message);
					}
					@Override
					public void onPMessage(String pattern, String channel, String message) {
						jedisSubscribe.onPMessage(pattern, channel, message);
					}
					@Override
					public void onSubscribe(String channel, int subscribedChannels) {
						jedisSubscribe.onSubscribe(channel, subscribedChannels);
					}
					@Override
					public void onUnsubscribe(String channel, int subscribedChannels) {
						jedisSubscribe.onUnsubscribe(channel, subscribedChannels);
					}
					@Override
					public void onPUnsubscribe(String pattern, int subscribedChannels) {
						jedisSubscribe.onPUnsubscribe(pattern, subscribedChannels);
					}
					@Override
					public void onPSubscribe(String pattern, int subscribedChannels) {
						jedisSubscribe.onPSubscribe(pattern, subscribedChannels);
					}
				}, channel);
			}
		});
		ExecutorService exService = Executors.newSingleThreadExecutor();
		exService.submit(thread);
	}
}
