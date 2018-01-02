package com.wsc.qa.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.wsc.qa.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService{
    private Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * set value
     * @param key
     * @param value
     * @return
     */
    @Override
	public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            logger.error("set error: key {}, value {}",key,value,e);
        }
        return result;
    }

    /**
     * set value with expireTime
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error("set error: key {}, value {},expireTime {}",key,value,expireTime,e);
        }
        return result;
    }

    /**
     * @param key
     * @return
     */
    @Override
	public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @param key
     * @return
     */
    @Override
	public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * remove single key
     * @param key
     */
    @Override
	public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * batch delete
     * @param keys
     */
    @Override
	public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * batch delete with pattern
     * @param pattern
     */
    @Override
	public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * hash set
     * @param key
     * @param hashKey
     * @param value
     */
    @Override
	public void hashSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * hash get
     * @param key
     * @param hashKey
     * @return
     */
    @Override
	public Object hashGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     *  list push
     * @param k
     * @param v
     */
    @Override
	public void push(String k,Object v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }

    /**
     *  list range
     * @param k
     * @param l
     * @param l1
     * @return
     */
    @Override
	public List<Object> range(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }

    /**
     *  set add
     * @param key
     * @param value
     */
    @Override
	public void setAdd(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * set get
     * @param key
     * @return
     */
    @Override
	public Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * ordered set add
     * @param key
     * @param value
     * @param scoure
     */
    @Override
	public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * rangeByScore
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    @Override
	public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }
}