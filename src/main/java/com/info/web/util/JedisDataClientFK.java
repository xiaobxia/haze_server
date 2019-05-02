package com.info.web.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis操作
 * 
 * @author WANGYUDONG
 * 
 */
@Slf4j
public class JedisDataClientFK {

	private static JedisPool poolMaster;

	private JedisDataClientFK() {

	}

	private synchronized static JedisPool initMasterPool() throws Exception {
		if (null == poolMaster) {
			String redisHostMaster;
			int redisPort;
			int timeout;
			try {
				redisHostMaster = RedisConfigConstantFK.getConstant("redis.host").trim();
				redisPort = Integer.parseInt(RedisConfigConstantFK.getConstant("redis.port").trim());
				timeout = Integer.parseInt(RedisConfigConstantFK.getConstant("redis.timeout").trim());
//				 redisHostMaster = "192.168.1.183";
//				 redisPort = 7002;
//				 timeout = 5000;
				log.info("redisHostMaster ip:{}",redisHostMaster);
				log.info("redis port:{}" ,redisPort);
				log.info("redis timeout:{}",timeout);
				JedisPoolConfig configMaster = new JedisPoolConfig();
				configMaster.setMaxTotal(1000);// 设置最大连接数
				configMaster.setMaxIdle(100); // 设置最大空闲数
				configMaster.setMinIdle(10);
				configMaster.setMaxWaitMillis(10000);// 设置超时时间
				configMaster.setTestWhileIdle(true);
				configMaster.setTestOnBorrow(false);
				poolMaster = new JedisPool(configMaster, redisHostMaster,
						redisPort, timeout);
			} catch (Exception e) {
				log.error("initMasterPool error:{}",e);
				throw e;
			}
		}
		return poolMaster;
	}

	private synchronized static Jedis getMasterJedis() throws Exception {
		if (poolMaster == null) {
			initMasterPool();
		}
		Jedis jedis = null;
		if (poolMaster != null) {
			jedis = poolMaster.getResource();
			jedis.auth(RedisConfigConstantFK.getConstant("redis.pass").trim());
		}
		return jedis;
	}

	private static void returnResource(final Jedis jedis,
			final JedisPool jedisPool) {
		if (jedis != null && jedisPool != null) {
			jedis.disconnect();
			jedisPool.returnResourceObject(jedis);
		}
	}

	/**
	 * 向redis加入数据
	 * 
	 * @param key key
	 * @param value val
	 * @throws Exception ex
	 */
	public static void set(String key, String value) throws Exception {
		Jedis jedisMaster = null;
		try {
			jedisMaster = getMasterJedis();
			jedisMaster.set(key, value);
		} finally {
			returnResource(jedisMaster, poolMaster);
		}
	}

	/**
	 * 向redis加入String类型数据,并设置保存时间
	 * 
	 * @param key key
	 * @param value val
	 * @param seconds
	 *            数据保存时间（单位：秒）
	 * @throws Exception ex
	 */
	public static void set(String key, String value, int seconds)
			throws Exception {
		Jedis jedisMaster = null;
		try {
			jedisMaster = getMasterJedis();
			jedisMaster.set(key, value);
			jedisMaster.expire(key, seconds);

		} finally {
			returnResource(jedisMaster, poolMaster);
		}
	}

	/**
	 * 从redis取值
	 * 
	 * @param key key
	 * @return str
	 * @throws Exception ex
	 */
	public static String get(String key) throws Exception {
		if (null == key || "".equals(key.trim())) {
			return null;
		}
		Jedis jedisMaster = null;
		String value;
		try {
			jedisMaster = getMasterJedis();
			value = jedisMaster.get(key);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				returnResource(jedisMaster, poolMaster);
			} catch (Exception e) {
				throw e;
			}
		}
		return value;
	}

	public static long del(String key) throws Exception {
		if (null == key || "".equals(key.trim())) {
			return 0;
		}
		long value = 0;
		Jedis jedisMaster = null;
		try {
			jedisMaster = getMasterJedis();
			value = jedisMaster.del(key);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				returnResource(jedisMaster, poolMaster);
			} catch (Exception e) {
				throw e;
			}
		}
		return value;
	}

	/**
	 * 续期
	 * 
	 * @param key key
	 * @param seconds se
	 * @throws Exception ex
	 */
	public static void expire(String key, int seconds) throws Exception {
		if (null == key || "".equals(key.trim())) {
			return;
		}
		Jedis jedisMaster = null;
		try {
			jedisMaster = getMasterJedis();
			jedisMaster.expire(key, seconds);
		} finally {
			returnResource(jedisMaster, poolMaster);
		}
	}

	/**
	 * 向List尾部加入数据，放入缓存，如果key不存在，创建该对象
	 * 
	 * @param key key
	 * @param list list
	 * @throws Exception ex
	 */
	public static void setList(String key, List<String> list, int... times)
			throws Exception {
		if (null == key || "".equals(key.trim())) {
			throw new Exception("the key can not be null.");
		}
		Jedis jedisMaster = null;
		try {
			jedisMaster = getMasterJedis();
			if (null != list && list.size() > 0) {
				jedisMaster.rpush(key, list.toArray(new String[list.size()]));
				if (null == times || times.length == 0) {
					expire(key, (int) TimeUnit.MINUTES.toSeconds(30));
				} else {
					expire(key, times[0]);
				}
				log.info(">>>>>>>>>>>list key=:{}",key);
			}
		} finally {
			returnResource(jedisMaster, poolMaster);
		}
	}

	/**
	 * 向List尾部加入单个数据，放入缓存，如果key不存在，创建该对象
	 * 
	 * @param key key
	 * @param listValue v
	 *            0:第一次插入,
	 * @throws Exception ex
	 */
	public static void setList(String key, String listValue) throws Exception {
		if (null == key || "".equals(key.trim())) {
			throw new Exception("the key can not be null.");
		}
		Jedis jedisMaster = null;
		try {
			jedisMaster = getMasterJedis();
			jedisMaster.lpush(key, listValue);
		} finally {
			returnResource(jedisMaster, poolMaster);
		}
	}

	/**
	 * Description:移除并返回列表的最后一个元素
	 * @throws Exception  e
	 */
	public static String rpop(String key) throws Exception{
		if (null == key || "".equals(key)) {
			throw new Exception("the key can not be null.");
		}
		Jedis jedisMaster = null;
		String value = "";
		try {
			jedisMaster = getMasterJedis();
			value = jedisMaster.rpop(key);
			log.info("FK get rpop date="+value);
		} finally {
			returnResource(jedisMaster, poolMaster);
		}
		return value;
	}

	/**
	 * 判断是否存在
	 * 
	 * @param key key
	 * @return b
	 * @throws Exception e
	 */
	public static boolean exists(String key) throws Exception {
		if (null == key || "".equals(key.trim())) {
			throw new Exception("the key can not be null.");
		}
		Jedis jedisMaster = null;
		try {
			jedisMaster = getMasterJedis();
			return jedisMaster.exists(key);
		} finally {
			returnResource(jedisMaster, poolMaster);
		}
	}

	/**
	 * 删除map中的某个/些键值
	 * 
	 * @param key key
	 * @param fields f
	 * @return long
	 * @throws Exception e
	 */
	public static Long hdel(String key, String... fields) throws Exception {
		if (null == key || "".equals(key.trim())) {
			throw new Exception("the key can not be null.");
		}
		Jedis jedisMaster = null;
		try {
			jedisMaster = getMasterJedis();
			return jedisMaster.hdel(key, fields);
		} finally {
			returnResource(jedisMaster, poolMaster);
		}
	}

	/**
	 * 从redis取所有的key
	 *
	 */
	public static String getKeys() throws Exception {
		Jedis jedisMaster = null;
		try {
			jedisMaster = getMasterJedis();
			Set<String> keys = jedisMaster.keys("*");
			Iterator<String> it = keys.iterator();
			StringBuffer sb = new StringBuffer();
			while (it.hasNext()) {
				String key = it.next();
			}
			return sb.toString();
		} finally {
			returnResource(jedisMaster, poolMaster);
		}
	}




//	public static void main(String[] args) throws Exception {
//		 delRediss();
//		// JedisDataClient.del();
//		// JedisDataClient.delRedisKeyByValue(tendListKey, tenderKey);
//		// getKeys();
//		// set("xyz", "0000");
//		//
//		// boolean b = exists("xyz");
//		//
//		// System.out.println(b);
//		// System.out.println(get("xyz"));
//		// {"flag":1,"id":10104,"tenderNum":21,"tenderRate":2.1,"userId":0}
////		delRedisKeyByValue("QBM_TWO_BORROW_TMC_10001",
////				"{\"flag\":1,\"id\":10105,\"tenderNum\":30,\"tenderRate\":3,\"userId\":0}");
//		setList("QBM_TWO_BORROW_TMC_10001",
//				"{\"flag\":0,\"id\":10105,\"tenderNum\":30,\"tenderRate\":3,\"userId\":0}");
//		getKeys();
//	}
}
