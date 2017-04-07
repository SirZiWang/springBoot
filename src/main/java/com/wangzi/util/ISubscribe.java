package com.wangzi.util;

public interface ISubscribe {
	public void onMessage(String channel, String message);
	public void onPMessage(String pattern, String channel, String message);
	public void onSubscribe(String channel, int subscribedChannels);
	public void onUnsubscribe(String channel, int subscribedChannels);
	public void onPUnsubscribe(String pattern, int subscribedChannels);
	public void onPSubscribe(String pattern, int subscribedChannels);
}
