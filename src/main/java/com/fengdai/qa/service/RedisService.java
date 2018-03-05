package com.fengdai.qa.service;

import java.util.List;
import java.util.Set;

public interface RedisService {

    /**
     * set value
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value);

    /**
     * set value with expireTime
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime);

    /**
     * @param key
     * @return
     */
    public boolean exists(final String key);

    /**
     * @param key
     * @return
     */
    public Object get(final String key);

    /**
     * remove single key
     * @param key
     */
    public void remove(final String key);

    /**
     * batch delete
     * @param keys
     */
    public void remove(final String... keys);

    /**
     * batch delete with pattern
     * @param pattern
     */
    public void removePattern(final String pattern);

    /**
     * hash set
     * @param key
     * @param hashKey
     * @param value
     */
    public void hashSet(String key, Object hashKey, Object value);

    /**
     * hash get
     * @param key
     * @param hashKey
     * @return
     */
    public Object hashGet(String key, Object hashKey);

    /**
     *  list push
     * @param k
     * @param v
     */
    public void push(String k,Object v);

    /**
     *  list range
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> range(String k, long l, long l1);

    /**
     *  set add
     * @param key
     * @param value
     */
    public void setAdd(String key,Object value);

    /**
     * set get
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key);

    /**
     * ordered set add
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,Object value,double scoure);

    /**
     * rangeByScore
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1);
}