package com.util.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.frame.core.util.SystemConfig;
import com.google.gson.Gson;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/** 
 * Redis的辅助类
 * @author wangch
 */  

public class RedisUtils {  
	//测试环境要用不同的KEY
	//public final static String PRE_KEY = SystemConfig.getValue("system.id") + "test_";
	public final static String PRE_KEY = SystemConfig.getValue("system.id") + "_";
	private static Log logger = LogFactory.getLog(RedisUtils.class);
    // 数据源  
    private JedisPool jedisPool; 
    private Gson gson = new Gson();
    
    public static RedisUtils instance() {
    	return new RedisUtils();
    }
    
    public RedisUtils() {
    	super();
    	try {
			jedisPool = com.util.RedisUtil.getJedisPool();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public RedisLock getLock(String key) {
    	return new RedisLock(this.preKey(key), jedisPool);  
    }
    
    /**
     * key是否存在
     * @param key
     * @return
     */
    public Boolean isExists(final String key1) {
    	final String key = preKey(key1);
    	return new RedisWork<Boolean>(jedisPool) {  
            @Override  
            public Boolean execute() {  
                return jedis.exists(key); 
            }  
        }.exec();  
    }
    
    public Boolean isNotExists(final String key1) {
    	final String key = preKey(key1);
    	return new RedisWork<Boolean>(jedisPool) {  
            @Override  
            public Boolean execute() {  
                return !jedis.exists(key); 
            }  
        }.exec();  
    }
    
    
    /** 
     * 删除模糊匹配的key 
     * @param likeKey 模糊匹配的key 
     * @return 删除成功的条数 
     */  
    public long delKeysLike(final String likeKey1) {  
    	final String likeKey = preKey(likeKey1);
        return new RedisWork<Long>(jedisPool) {  
  
            @Override  
            public Long execute() {  
                Set<String> keys = jedis.keys(likeKey + "*");  
                return jedis.del(keys.toArray(new String[keys.size()]));  
            }  
        }.exec();  
    }  
  
    /** 
     * 删除 
     * @param key 匹配的key 
     * @return 删除成功的条数 
     */  
    public Long delKey(final String key1) {  
    	final String key = preKey(key1);
        return new RedisWork<Long>(jedisPool) {  
  
            @Override  
            public Long execute() {  
                return jedis.del(key);  
            }  
        }.exec();  
    }  
  
    /** 
     * 删除 
     * @param keys 匹配的key的集合 
     * @return 删除成功的条数 
     */  
    public Long delKey(String[] keys1) { 
    	final String[] keys = preKey(keys1);
        return new RedisWork<Long>(jedisPool) {  
  
            @Override  
            public Long execute() {  
                return jedis.del(keys);  
            }  
        }.exec();  
    }  
    
    /** 
     * 从key对应list中删除count个和value相同的元素
     * 
     * count=0时，删除全部
     * count<0时，按从尾到头的顺序删除
     * count>0时，按从头到尾的顺序删除
     */  
    public Long listRemove(final String key1, final int count, final String value) {  
    	final String key = preKey(key1);
        return new RedisWork<Long>(jedisPool) {  
  
            @Override  
            public Long execute() {  
            	return jedis.lrem(key, count, value);
            }  
        }.exec();  
    }  
  
    /** 
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。 
     * 在 Redis 中，带有生存时间的 key 被称为『可挥发』(volatile)的。 
     * @param key key 
     * @param expire 生命周期，单位为秒 
     * @return 1: 设置成功 0: 已经超时或key不存在 
     */  
    public Long expire(final String key1, final int expire) {  
    	final String key = preKey(key1);
        return new RedisWork<Long>(jedisPool) {  
  
            @Override  
            public Long execute() {  
                return jedis.expire(key, expire);  
            }  
        }.exec();  
    }  
  
    /** 
     * 一个redis的id生成器，利用了redis原子性自增操作的特点 (应用负载时也可使用
     * 最大可以生成Long.MAX_VALUE 的，大于Long.MAX_VALUE 又从0开始
     * @param key id的key 
     * @return 返回生成的Id 
     */  
    public long makeId(final String key1) {  
    	final String key = preKey(key1);
        return new RedisWork<Long>(jedisPool) {  
  
            @Override  
            public Long execute() {  
                long id = jedis.incr(key);  
                if ((id + 80000) >= Long.MAX_VALUE) {  
                    // 避免溢出，重置，getSet命令之前允许incr插队，80000就是预留的插队空间  
                    jedis.getSet(key, "0");  
                }  
                return id;  
            }  
        }.exec();  
    }  
  
    /** ======================================String/Object====================================== **/  
  
    /** 
     * 将字符串值 value 关联到 key 。 
     * 如果 key 已经持有其他值， setString 就覆写旧值，无视类型。 
     * 对于某个原本带有生存时间（TTL）的键来说， 当 setString 成功在这个键上执行时， 这个键原有的 TTL 将被清除。 
     * @param key key 
     * @param value string或者JSONObject.fromObject  JSONObArray.fromObject能转换的类
     * @return 在设置操作成功完成时，才返回 OK 。 
     */  
    public String set(final String key1, final Object value) {  
    	final String key = preKey(key1);
        return new RedisWork<String>(jedisPool) {  
  
            @Override  
            public String execute() {  
                return jedis.set(key, toStr(value));  
            }  
        }.exec();  
    }  
  
    /** 
     * 将值 value 关联到 key ，并将 key 的生存时间设为 expire (单位秒)。 
     * 如果 key 已经存在， 将覆写旧值。 
     * 类似于以下两个命令: 
     * SET key value 
     * EXPIRE key expire # 设置生存时间 
     * 不同之处是这个方法是一个原子性(atomic)操作，关联值和设置生存时间两个动作会在同一时间内完成，在 Redis 用作缓存时，非常实用。 
     * @param key key 
     * @param value string 或者JSONObject.fromObject  JSONObArray.fromObject能转换的类 
     * @param expire 生命周期 
     * @return 设置成功时返回 OK 。当 expire 参数不合法时，返回一个错误。 
     */  
    public String set(final String key1, final Object value, final int expire) {  
    	final String key = preKey(key1);
        return new RedisWork<String>(jedisPool) {  
  
            @Override  
            public String execute() {  
                return jedis.setex(key, expire, toStr(value));  
            }  
        }.exec();  
    }  
  
    /** 
     * 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 setNotExists 不做任何动作。 
     * @param key key 
     * @param value string 或者JSONObject.fromObject  JSONObArray.fromObject能转换的类 
     * @return 设置成功，返回 1 。设置失败，返回 0 。 
     */  
    public Long setNotExists(final String key1, final Object value) { 
    	final String key = preKey(key1);
        return new RedisWork<Long>(jedisPool) {  
  
            @Override  
            public Long execute() {  
                return jedis.setnx(key, toStr(value));  
            }  
        }.exec();  
    }  
  
    /** 
     * 返回 key 所关联的字符串值。如果 key 不存在那么返回特殊值 nil 。 
     * 假如 key 储存的值不是字符串类型，返回一个错误，因为 getString 只能用于处理字符串值。 
     * @param key key 
     * @return 当 key 不存在时，返回 nil ，否则，返回 key 的值。如果 key 不是字符串类型，那么返回一个错误。 
     */  
    public String get(final String key1) {  
    	final String key = preKey(key1);
        return new RedisWork<String>(jedisPool) {  
  
            @Override  
            public String execute() {  
                return jedis.get(key);  
            }  
        }.exec();  
    }  
    
    public <T> T get(final String key1, Class<T> cls) {  
    	
    	String val = get(key1);
    	
    	return toObj(val, cls);
    }
  
    /** 
     * 批量的 setString(String, Object) 
     * @param pairs 键值对数组{数组第一个元素为key，第二个元素为value} 
     * @return 操作状态的集合 
     */  
    public List<Object> batchSet(final List<Pair<String, Object>> pairs) {  
        return new RedisWork<List<Object>>(jedisPool) {  
  
            @Override  
            public List<Object> execute() {  
                Pipeline pipeline = jedis.pipelined();  
                for (Pair<String, Object> pair : pairs) {  
                    pipeline.set(pair.redisKey(), pair.redisValue());  
                }  
                return pipeline.syncAndReturnAll();  
            }  
        }.exec();  
    }  
    
    /** 
     * 批量的getString(String) 
     * @param keys key数组 
     * @return value的集合 
     */  
    public List<String> batchGet(final String[] keys1) {  
    	final String[] keys = preKey(keys1);
        return new RedisWork<List<String>>(jedisPool) {  
  
            @Override  
            public List<String> execute() {  
            	Pipeline pipeline = jedis.pipelined();  
                List<String> result = new ArrayList<String>(keys.length);  
                List<Response<String>> responses = new ArrayList<Response<String>>(keys.length);  
                for (String key : keys) {  
                    responses.add(pipeline.get(key));  
                }  
                pipeline.sync();  
                for (Response<String> resp : responses) {  
                    result.add(resp.get());  
                }  
                return result;  
            }  
        }.exec();  
    }  
    
    /* ======================================List====================================== */  
  
    /** 
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。 
     * @param key key 
     * @param values value的数组 
     */  
    public Long listPushTail(final String key1, final Object... values) { 
    	final String key = preKey(key1);
        return new RedisWork<Long>(jedisPool) {  
  
            @Override  
            public Long execute() {  
            	 RedisLock lock = new RedisLock(key, jedisPool);  
                 lock.lock();  
                 try {  
                     Pipeline pipeline = jedis.pipelined();  
                     for (Object value : values) {  
                    	 pipeline.rpush(key, toStr(value));  
                     }  
                     pipeline.sync(); 
                 } finally {  
                     lock.unlock();  
                 }  
                 return null;
                //return jedis.rpush(key, values);  
            }  
        }.exec();  
    }
  
    /** 
     * 将一个或多个值 value 插入到列表 key 的表头 
     * @param key key 
     * @param value string value 
     * @return 执行 listPushHead 命令后，列表的长度。 
     */  
    public Long listPushHead(final String key1, final Object... values) {  
    	final String key = preKey(key1);
        return new RedisWork<Long>(jedisPool) {  
  
            @Override  
            public Long execute() {  
            	 RedisLock lock = new RedisLock(key, jedisPool);  
                 lock.lock();  
                 try {  
                     Pipeline pipeline = jedis.pipelined();  
                     for (Object value : values) {  
                         pipeline.lpush(key, toStr(value));  
                     }  
                     pipeline.sync();  
                 } finally {  
                     lock.unlock();  
                 }  
                 return null;
            }  
        }.exec();  
    }  
  
    
  
    /** 
     * 批量的listPushTail(String, String...)，以锁的方式实现 
     * @param key key 
     * @param values value的数组 
     * @param delOld 如果key存在，是否删除它。true 删除；false: 不删除，只是在行尾追加 
     */  
   public void listBatchPushTail(final String key1, final Object[] values, final boolean delOld) {
	   final String key = preKey(key1);
        new RedisWork<Object>(jedisPool) {  
  
            @Override  
            public Object execute() {  
                if (delOld) {  
                    RedisLock lock = new RedisLock(key, jedisPool);  
                    lock.lock();  
                    try {  
                        Pipeline pipeline = jedis.pipelined();  
                        pipeline.del(key);  
                        for (Object value : values) {  
                            pipeline.rpush(key, toStr(value));  
                        }  
                        pipeline.sync();  
                    } finally {  
                        lock.unlock();  
                    }  
                } else { 
                	 RedisLock lock = new RedisLock(key, jedisPool);  
                     lock.lock();  
                     try {  
                         Pipeline pipeline = jedis.pipelined();  
                         for (Object value : values) {  
                             pipeline.rpush(key, toStr(value));  
                         }  
                         pipeline.sync();  
                     } finally {  
                         lock.unlock();  
                     }  
                }  
                return null;  
            }  
        }.exec();  
    }  
  
    /** 
     * 同batchListPushTail(String, String[], boolean),不同的是利用redis的事务特性来实现 
     * @param key key 
     * @param values value的数组  数组里的元素要JSONObject.fromObject  JSONObArray.fromObject能转换的类
     * @return null 
     */  
    public Object listUpdateInTransaction(final String key1, final Object[] values) {  
    	final String key = preKey(key1);
        return new RedisWork<Object>(jedisPool) {  
  
            @Override  
            public Object execute() {  
                Transaction transaction = jedis.multi();  
                transaction.del(key);  
                for (Object value : values) {  
                    transaction.rpush(key, toStr(value));  
                }  
                transaction.exec();  
                return null;  
            }  
        }.exec();  
    }  
  
    /** 
     * 在key对应list的尾部部添加字符串元素,如果key存在，什么也不做 
     * @param key key 
     * @param values value的数组 数组里的元素要JSONObject.fromObject  JSONObArray.fromObject能转换的类 
     * @return 执行listInsertNotExists后，表的长度 
     */  
    public Long listInsertNotExists(final String key1, final Object[] values) {  
    	final String key = preKey(key1);
        return new RedisWork<Long>(jedisPool) {  
  
            @Override  
            public Long execute() {  
                RedisLock lock = new RedisLock(key, jedisPool);  
                lock.lock();  
                try {  
                    if (!jedis.exists(key)) {  
                    	 Pipeline pipeline = jedis.pipelined();  
                         for (Object value : values) {  
                             pipeline.rpush(key, toStr(value));  
                         }  
                         pipeline.sync(); 
                    }  
                } finally {  
                    lock.unlock();  
                }  
                return 0L;  
            }  
        }.exec();  
    }  
  
    /** 
     * 返回list所有元素，下标从0开始，负值表示从后面计算，-1表示倒数第一个元素，key不存在返回空列表 
     * @param key key 
     * @return list所有元素 
     */  
    public List<String> listGetAll(final String key1) {  
    	final String key = preKey(key1);
        return new RedisWork<List<String>>(jedisPool) {  
  
            @Override  
            public List<String> execute() {  
                return jedis.lrange(key, 0, -1);  
            }  
        }.exec();  
    }  
    
    public <T> List<T> listGetAll(final String key1, final Class<T> cls) {
    	List<String> list = listGetAll(key1);
    	return this.toObjList(list,cls);
    } 
    
  
    /** 
     * 返回指定区间内的元素，下标从0开始，负值表示从后面计算，-1表示倒数第一个元素，key不存在返回空列表 
     * @param key key 
     * @param beginIndex 下标开始索引（包含） 
     * @param endIndex 下标结束索引（不包含）
     * @return 指定区间内的元素 
     */  
    public List<String> listRange(final String key1, final long beginIndex, final long endIndex) {  
    	final String key = preKey(key1);
        return new RedisWork<List<String>>(jedisPool) {  
  
            @Override  
            public List<String> execute() {  
                //return jedis.lrange(key, beginIndex, endIndex - 1);  
                return jedis.lrange(key, beginIndex, endIndex);  
            }  
        }.exec();  
    } 
    
    public <T> List<T> listRange(final String key1, final long beginIndex, final long endIndex, Class<T> cls) {  
    	List<String> list = listRange(key1, beginIndex, endIndex);
    	return this.toObjList(list, cls);
    }
    
  
    /** 
     * 一次获得多个链表的数据，返回的是string
     * @param keys key的数组 
     * @return 执行结果 
     */  
    public Map<String, List<String>> listBatchGetAll(final List<String> keys1) {  
    	final List<String> keys = preKey(keys1);
        return new RedisWork<Map<String, List<String>>>(jedisPool) {  
  
            @Override  
            public Map<String, List<String>> execute() {  
                Pipeline pipeline = jedis.pipelined();  
                Map<String, List<String>> result = new HashMap<String, List<String>>();  
                List<Response<List<String>>> responses = new ArrayList<Response<List<String>>>(keys.size());  
                for (String key : keys) {  
                    responses.add(pipeline.lrange(key, 0, -1));  
                }  
                pipeline.sync();  
                for (int i = 0; i < keys.size(); ++i) {  
                    result.put(keys.get(i), responses.get(i).get());  
                }  
                return result;  
            }  
        }.exec();  
    }
   
    
    
    /* ======================================Other====================================== */  
    
    /**
     * 为了区分各系统的Key，生成相应加个前缀的KEY
     */
    private String preKey(String key) {
    	return PRE_KEY + key;
    }
    
    private String[] preKey(String[] keys) {
    	List<String> ks = new ArrayList<String>();
    	for (String key : keys) {
    		ks.add(preKey(key));
    	}
    	return (String[])ks.toArray();
    }
    
    private List<String> preKey(List<String> keys) {
    	List<String> ks = new ArrayList<String>();
    	for (String key : keys) {
    		ks.add(preKey(key));
    	}
    	return ks;
    }
    
    public String toStr(Object value) {
    	if (value == null) {
    		return "";
    	}
    	if (value instanceof String) {
			return (String)value;
		} 
    	JSON json = null;
		if (value instanceof List) {
			json = JSONArray.fromObject(value);
		} else {
			json = JSONObject.fromObject(value);
		}
		return json.toString();
    }
    
    public static <T> T toObj(String str, Class<T> clazz) {
    	if (str == null || str.isEmpty()) {
    		return null;
    	}
    	Gson gson = new Gson();
    	return (T)gson.fromJson(str, clazz);
    }
    
    public <T> List<T> toObjList(List<String> strList, Class<T> clazz) {
    	if (strList == null || strList.isEmpty()) {
    		return null;
    	}
    	List<T> listObj = new ArrayList<T>();
    	for (String str : strList) {
    		listObj.add(gson.fromJson(str, clazz));
    	}
    	return listObj;
    }
    
    public JedisPool getPool() {
		return jedisPool;
	}
    
    /** 
     * 设置数据源 
     * @param jedisPool 数据源 
     */  
    /*public void setJedisPool(JedisPool jedisPool) { 
        this.jedisPool = jedisPool;  
    } */ 
  
    /** 
     * 构造Pair键值对 
     * @param key key 
     * @param value value 
     * @return 键值对 
     */  
    public <K, V> Pair<K, V> makePair(K key, V value) {  
        return new Pair<K, V>(key, value);  
    }  
    //
    /** 
     * 构造key对应的class键值对 
     * @param key key 
     * @param cls class 
     * @return 键值对 
     */ 
    public PairClass makePairClass(String key, Class<?> cls) {  
        return new PairClass(key, cls);  
    }
  
    /** 
     * 键值对 
     * @version V1.0 
     * @param <K> key 
     * @param <V> value 
     */  
    public class Pair<K, V> {  
  
        private K key;  
        private V value;  
  
        public Pair(K key, V value) {  
            this.key = key;  
            this.value = value;  
        }  
  
        public K getKey() {  
            return key;  
        }  
  
        public void setKey(K key) {  
            this.key = key;  
        }  
  
        public V getValue() {  
            return value;  
        }  
  
        public void setValue(V value) {  
            this.value = value;  
        }  
        
		//加了系统前缀的Key
        public String redisKey() {  
            return PRE_KEY + key;  
        }  
        //obj--string
        public String redisValue() { 
        	
        	JSON obj = null;
        	
        	if (value instanceof String) {
 				return (String)value;
 			} 
        	 
			if (value instanceof List) {
				obj = JSONArray.fromObject(value);
			} else {
				obj = JSONObject.fromObject(value);
			}
            return obj.toString();  
        } 
    }  
    
    /** 
     * 键值对 
     * @param String key 
     * @param Class clazz 
     */  
    public class PairClass {  
  
        private String key;  
        private Class clazz;
  
        public PairClass(String key, Class clazz) {  
            this.key = key;  
            this.clazz = clazz;  
        }  
        
        public String getKey() { 
            return key;  
        }
        
       //加了系统前缀的Key
        public String redisKey() {  
            return PRE_KEY + key;  
        }  

		public Class getClazz() {
			return clazz;
		}

    }  
}  