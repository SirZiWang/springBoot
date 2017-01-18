package com.wangzi.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("rawtypes")
public class ResidService {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Resource(name="stringRedisTemplate")
	private ValueOperations<String, String> stringRedisOpertions;
	
	@Autowired
	private RedisTemplate redisTemplate;
	@Resource(name="redisTemplate")
	private ValueOperations<Object, Object> objectResidOperations;
	
	/**
	 * redis 插入数据
	 * 默认过期时间
	 * @param key
	 * @param value
	 */
	public void setStringValue(final String key, String value){
		stringRedisOpertions.set(key, value);
	}
	/**
	 * redis查询数据
	 * @param key
	 * @return
	 */
	public String getStringValue(final String key){
		return stringRedisOpertions.get(key);
	}
	/**
	 * redis 插入数据 
	 * 设置过期时间
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public void setStringValue(final String key, String value, long expireTime){
		stringRedisOpertions.set(key, value);
		stringRedisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
	}
	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public boolean hashKey(final String key){
		return stringRedisTemplate.hasKey(key);
	}
	/**
	 * 删除相应的value
	 * @param key
	 */
	public void remove(final String key){
		stringRedisTemplate.delete(key);
	}
	
	public StringRedisTemplate geStringRedisTemplate(){
		return this.stringRedisTemplate;
	}
	
	public RedisTemplate geRedisTemplate(){
		return this.redisTemplate;
	}
	
	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}
	
	/**
	 * 返回 StringRedisOperation
	 * @return
	 */
	public ValueOperations<String, String> getOperations(){
		return this.stringRedisOpertions;
	}
	
	/**
	 *  更新缓存中的数据
	 * @param key
	 * @param delta
	 * @return
	 */
	public long updateIncrement(String key, long delta){
		return stringRedisOpertions.increment(key, delta);
	}
	/**
	 * RedisTemplate<br>
	 * 获取对象类型的值
	 * @param key
	 * @return
	 */
	public Object getObjectValue(String key){
		return objectResidOperations.get(key);
	}
	
	/**
	 * RedisTemplate<br>
	 * 添加对象类型数据
	 * @param key
	 * @param object
	 */
	public void setObjectValue(String key,Object object){
		objectResidOperations.set(key, object);
	}
	
	/**
	 * RedisTemplate<br>
	 * @param key
	 * @param object
	 * @param expireTime
	 */
	@SuppressWarnings("unchecked")
	public void setObjectValue(String key,Object object ,Long expireTime){
		objectResidOperations.set(key, object);
		redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
	}
	
	/**
	 * 获取自增值
	 * @param key
	 * @return
	 */
	public int getIncrValue(final String key) {  
	      
	    return stringRedisTemplate.execute(new RedisCallback<Integer>() {
	    	@Override  
	        public Integer doInRedis(RedisConnection connection) throws DataAccessException {  
	            RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();  
	            byte[] rowkey=serializer.serialize(key);  
	            byte[] rowval=connection.get(rowkey);  
	            try {  
	                String val=serializer.deserialize(rowval);
	                return Integer.parseInt(val);  
	            } catch (Exception e) {  
	                return 0;  
	            }  
	        }
		});  
	}

}
