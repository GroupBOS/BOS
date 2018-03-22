package com.robin.bos.fore.dao.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**  
 * ClassName:RedisTemplateTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午3:42:55 <br/>       
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisTemplateTest {
    
    //Spring提供的
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    
    
    @Test
    public void test01()
    {
        //
        ValueOperations<String, String> dbOperation = redisTemplate.opsForValue();
        dbOperation.set("robin", "test");
    }
    
    
    @Test
    public void test02()
    {
        //
        ValueOperations<String, String> dbOperation = redisTemplate.opsForValue();
        String result = dbOperation.get("robin");
        System.out.println(result);
    }
    
    @Test
    public void test03() throws InterruptedException
    {
        //
        ValueOperations<String, String> dbOperation = redisTemplate.opsForValue();
        dbOperation.set("robin", "just so so", 10, TimeUnit.SECONDS);
        
        String result = dbOperation.get("robin");
        System.out.println(result);
        
        Thread.sleep(1000*11);
        
        String result2 = dbOperation.get("robin");
        System.out.println(result2);
        
    }

}
  
