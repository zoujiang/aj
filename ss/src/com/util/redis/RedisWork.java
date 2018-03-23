package com.util.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 执行类，RedisUtil的辅助类， 
 * 它保证在执行操作之后释放数据源returnResource(jedis) 
 * @author wangch
 * */
public abstract class RedisWork<T> {  
	private Log logger = LogFactory.getLog(RedisWork.class);  
    public Jedis jedis;  
    public JedisPool jedisPool;  

    public RedisWork(JedisPool jedisPool) {  
        this.jedisPool = jedisPool;  
        jedis = this.jedisPool.getResource();  
    }  

    /** 
     * 回调 
     * @return 执行结果 
     */  
    public abstract T execute();  

    /** 
     * 调用execute()并返回执行结果 
     * 它保证在执行execute()之后释放数据源returnResource(jedis) 
     * @return 执行结果 
     */  
    public T exec() {  
        T result = null;  
        try {  
            result = execute();  
            logger.info("exec-rel:" + result);
            //System.out.println("result:" + result);
        } catch (Exception e) {  
        	e.printStackTrace();
        	logger.info("exec-error:" + e.toString(), e);
        	if (jedis != null) {
        		jedisPool.returnBrokenResource(jedis);  
        	}
        	
        	//jedis = null;
            throw new RuntimeException("Redis execute exception", e);  
        } finally {  
            if (jedis != null) {  
                jedisPool.returnResource(jedis);  
            }  
        }  
        return result;  
    }  
   
}  
