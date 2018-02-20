package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.frame.core.util.SystemConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisUtil {

	protected static final Logger log = Logger.getLogger(RedisUtil.class
			.getName());

	private static boolean isInitRdis = false;

	// redis服务IP和端口
	private static final String ip = SystemConfig.getValue("wcs.redis.ip");
	private static final int port = Integer.parseInt(SystemConfig
			.getValue("wcs.redis.port"));
	private static final String password = SystemConfig
			.getValue("wcs.redis.password");

	private static JedisPool pool = null;

	/**
	 * 初始化redis
	 */
	private static void setUp() throws Exception {
		log.info("begin");
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；如果赋值为-1，则表示不限制
			config.setMaxActive(4000);
			// 最多有多少个状态为idle(空闲)的jedis实例
			config.setMaxIdle(4000);
			// 在获取连接的时候检查有效性
			config.setTestOnBorrow(true);
			pool = new JedisPool(config, ip, port, 60000, password);
			//pool = new JedisPool(config, ip, port);
			isInitRdis = true;
			log.info("end");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("e.getMessage():" + e.getMessage());
			throw e;
		}
	}

	/**
	 * 获取JedisPool
	 * 
	 * @return JedisPool
	 */
	public static JedisPool getJedisPool() throws Exception {
		try {
			if (!isInitRdis || pool == null) {
				log.info("setUp(),isInitRdis is false || pool == null");
				setUp();
			}
			return pool;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("e.getMessage():" + e.getMessage());
			throw e;
		}
	}

	/**
	 * 获取Jedis
	 * 
	 * @return Jedis
	 */
	public static Jedis getJedis() throws Exception {
		log.info("begin");
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			if (!jedis.isConnected()) {
				log.info("连接jedis");
				jedis.connect();
				log.info("jedis.isConnected=" + jedis.isConnected());
			}
			log.info("end");
			return jedis;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("e.getMessage():" + e.getMessage());
			getJedisPool().returnBrokenResource(jedis);
			throw e;
		}
	}

	/**
	 * 返还jedis资源
	 * 
	 * @param jedis
	 * @throws Exception
	 */
	public static void returnResource(Jedis jedis) throws Exception {
		try {
			log.info("begin");
			jedis.disconnect();
			getJedisPool().returnResource(jedis);
			log.info("end");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("e.getMessage():" + e.getMessage());
			throw e;
		}
	}

	/**
	 * 返还异常的jedis资源
	 * 
	 * @param jedis
	 * @throws Exception
	 */
	public static void returnBrokenResource(Jedis jedis) throws Exception {
		try {
			log.info("begin");
			jedis.disconnect();
			getJedisPool().returnBrokenResource(jedis);
			log.info("end");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("e.getMessage():" + e.getMessage());
			throw e;
		}
	}
	/**
	 * 模糊根据key获取所有匹配的值
	 * */
	public static List<Map<String, Object>> getValueByKeyPatten(String keyPatten){
		
		log.info("模糊查询时Patten:"+keyPatten);
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Jedis jedis = getJedis();
			Set<String> keys = jedis.keys(keyPatten);
			for(String key : keys){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(key, jedis.get(key));
				list.add(map);
			}
			log.info("模糊查询时all values:"+list.toString());
			return list ;
		} catch (Exception e) {
			log.info("模糊查询时异常:"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}