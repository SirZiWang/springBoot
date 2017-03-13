package com.wangzi.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public enum RedisCli {

	instance;

	private Logger logger = LoggerFactory.getLogger(RedisCli.class);

	private Config config = null;

	private RedissonClient redisson = null;

	private RedisCli() {
		try {
			config = Config.fromJSON(new File("conf/activityCenter/redis/redisson/config.properties"));
			logger.info("opt [RedisCli] flow [redis config] succ : {}",config.toJSON());
			redisson = Redisson.create(config);
			logger.info("opt [RedisCli] flow [redis create] succ : {}",redisson);
		} catch (IOException e) {
			logger.error("opt [RedisCli] exception detail : {}",e);
		}
	}
	
	public RedissonClient getClient(){
		return redisson;
	}
}
