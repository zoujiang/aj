package com.util.redis;

import java.util.Date;
import java.util.Random;

import com.frame.core.util.DateUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/** 
 * Redis的辅助类
 * @author wangch
 */ 
public class RedisLock {  
    
    /** 加锁标志 */  
    public static final String LOCKED = "TRUE";  
    /** 毫秒与毫微秒的换算单位 1毫秒 = 1000000毫微秒 */  
    public static final long MILLI_NANO_CONVERSION = 1000 * 1000L;  
    /** 默认超时时间（毫秒） */  
    public static final long DEFAULT_TIME_OUT = 5000;  
    public static final Random RANDOM = new Random();  
    /** 锁的超时时间（秒），过期删除 */  
    public static final int EXPIRE = 3 * 60;  
  
    private JedisPool jedisPool;  
    private Jedis jedis;  
    private String key;  
    // 锁状态标志  
    private boolean locked = false;  
  
    /** 
     * This creates a RedisLock 
     * @param key key 
     * @param jedisPool 数据源 
     */  
    public RedisLock(String key, JedisPool jedisPool) {  
        this.key = key + "_lock";  
        this.jedisPool = jedisPool;  
        this.jedis = this.jedisPool.getResource();  
    }  
  
    /** 
     * 加锁 
     * 应该以： 
     * lock(); 
     * try { 
     *      doSomething(); 
     * } finally { 
     *      unlock()； 
     * } 
     * 的方式调用  
     * @param timeout 超时时间 
     * @return 成功或失败标志 
     */  
    public boolean lock(long timeout) { 
    	
    	return lock(timeout, EXPIRE);
    	
        /*long nano = System.nanoTime();  
        timeout *= MILLI_NANO_CONVERSION;  
        try {  
            while ((System.nanoTime() - nano) < timeout) {  
            	String lockValue = LOCKED + "_" + DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN4);
                if (this.jedis.setnx(this.key, lockValue) == 1) {  
                    this.jedis.expire(this.key, EXPIRE);  
                    this.locked = true;  
                    return this.locked;  
                }   
                // 短暂休眠，避免出现活锁  
                Thread.sleep(3, RANDOM.nextInt(500));  
            }  
        } catch (Exception e) {  
            throw new RuntimeException("Locking error", e);  
        }  
        return false;  */
    }  
  
    /** 
     * 加锁 
     * 应该以： 
     * lock(); 
     * try { 
     *      doSomething(); 
     * } finally { 
     *      unlock()； 
     * } 
     * 的方式调用 
     * @param timeout 超时时间 
     * @param expire 锁的超时时间（秒），过期删除 
     * @return 成功或失败标志 
     */  
    public boolean lock(long timeout, int expire) {  
        long nano = System.nanoTime();  
        timeout *= MILLI_NANO_CONVERSION;  
        try {  
            while ((System.nanoTime() - nano) < timeout) {  
            	String lockValue = LOCKED + "_" + DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN4);
                if (this.jedis.setnx(this.key, lockValue) == 1) {  
                    this.jedis.expire(this.key, expire);  
                    this.locked = true;  
                    return this.locked;  
                }  
                // 短暂休眠，避免出现活锁  
                Thread.sleep(3, RANDOM.nextInt(500));  
            }  
        } catch (Exception e) {  
        	e.printStackTrace();
            throw new RuntimeException("Locking error", e);  
        }  
        return false;  
    }  
  
    /** 
     * 加锁 
     * 应该以： 
     * lock(); 
     * try { 
     *      doSomething(); 
     * } finally { 
     *      unlock()； 
     * } 
     * 的方式调用 
     * @return 成功或失败标志 
     */  
    public boolean lock() {  
        return lock(DEFAULT_TIME_OUT);  
    }  
  
    /** 
     * 解锁 
     * 无论是否加锁成功，都需要调用unlock 
     * 应该以： 
     * lock(); 
     * try { 
     *      doSomething(); 
     * } finally { 
     *      unlock()； 
     * } 
     * 的方式调用 
     */  
    public void unlock() {  
        try {  
            if (this.locked) {  
                this.jedis.del(this.key);  
            }  
        } catch (Exception e) {  
        	e.printStackTrace();
        	if (this.jedis != null) {
        		this.jedisPool.returnBrokenResource(this.jedis);  
        	}
            throw new RuntimeException("Locking error", e);  
        }  finally {  
        	if (this.jedis != null) {
        		this.jedisPool.returnResource(this.jedis);  
        	}
        }  
    }  
}  