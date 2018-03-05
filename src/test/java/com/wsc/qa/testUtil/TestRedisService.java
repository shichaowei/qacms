package com.wsc.qa.testUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fengdai.qa.service.RedisService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisService {
	@Autowired
	private RedisService redisServiceImpl;

	 @Autowired
	    private RedisTemplate redisTemplate;

    @Test
    public void testString() throws Exception {
    	redisServiceImpl.set("neo", "123");
        Assert.assertEquals("ityouknow", redisServiceImpl.get("neo"));
    }

    @Test
    public void testString1() throws Exception {
    	redisTemplate.opsForValue().set("123", "456");
    }

    @Test
    public void testObj() throws Exception {
    	redisServiceImpl.set("logtome", "listening",30L);
    	Thread.sleep(5000);
    	System.out.println("sfdsfdf:"+redisServiceImpl.get("logtome"));
    }


}