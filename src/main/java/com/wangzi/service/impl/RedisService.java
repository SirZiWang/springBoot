package com.wangzi.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

@SuppressWarnings("rawtypes")
@Service
public class RedisService {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Resource(name = "stringRedisTemplate")
	private ValueOperations<String, String> stringRedisOperations;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Resource(name = "redisTemplate")
	private ValueOperations<Object, Object> objectRedisOperations;
	
	/**
	 * StringRedisTemplate<br>
	 * 放入redis,使用默认的过期时间<br>
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public void setStringValue(final String key,String value){
		stringRedisOperations.set(key, value);
	}
	
	/**
	 * StringRedisTemplate<br>
	 * 从redis取出value
	 * @param key
	 * @return
	 */
	public String getStringValue(final String key){
		return stringRedisOperations.get(key);
	}
	
	/**
	 * StringRedisTemplate<br>
	 * 放入redis,并设置过期时间(分钟)
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public void setStringValue(final String key,String value,Long expireTime){
		stringRedisOperations.set(key, value);
		stringRedisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * StringRedisTemplate<br>
	 * 判断是否存在key
	 * @param key
	 * @return
	 */
	public boolean hasKey(final String key){
		return stringRedisTemplate.hasKey(key);
	}
	
	/**
	 * StringRedisTemplate<br>
	 * 删除相应的value
	 * @param key
	 */
	public void remove(final String key){
		stringRedisTemplate.delete(key);
	}
	
	
	public StringRedisTemplate getStringRedisTemplate(){
		return this.stringRedisTemplate;
	}
	
	public RedisTemplate getRedisTemplate(){
		return this.redisTemplate;
	}
	
	/**
	 * StringRedisTemplate<br>
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
	 * StringRedisTemplate<br>
	 * 返回 StringRedisOperation
	 * @return
	 */
	public ValueOperations<String, String> getOperations(){
		return this.stringRedisOperations;
	}
	
	/**
	 *  更新缓存中的数据
	 * @param key
	 * @param delta
	 * @return
	 */
	public long updateIncrement(String key, long delta){
		return stringRedisOperations.increment(key, delta);
	}
	/**
	 * RedisTemplate<br>
	 * 获取对象类型的值
	 * @param key
	 * @return
	 */
	public Object getObjectValue(String key){
		return objectRedisOperations.get(key);
	}
	
	/**
	 * RedisTemplate<br>
	 * 添加对象类型数据
	 * @param key
	 * @param object
	 */
	public void setObjectValue(String key,Object object){
		objectRedisOperations.set(key, object);
	}
	
	/**
	 * RedisTemplate<br>
	 * @param key
	 * @param object
	 * @param expireTime
	 */
	@SuppressWarnings("unchecked")
	public void setObjectValue(String key,Object object ,Long expireTime){
		objectRedisOperations.set(key, object);
		redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
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
	
	public void expire(String key,Long expireTime){
		redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
	}
	
	public void increment(String key,Long delta){
		objectRedisOperations.increment(key, delta);
	}
	
	@PostConstruct
	public void init(){
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
	}
	
}

