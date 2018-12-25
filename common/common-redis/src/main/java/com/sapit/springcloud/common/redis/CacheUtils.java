package com.sapit.springcloud.common.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

import com.sapit.springcloud.common.util.SpringContextHolder;

/**
 * Cache工具类
 * 
 * @author sapit
 * @version 2013-5-29
 */
public class CacheUtils {
	private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
	private static final String SYS_CACHE = "sys-cache";

	/**
	 * 获取SYS_CACHE缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		ValueWrapper valueWrapper = get(SYS_CACHE, key);
		if (valueWrapper != null && valueWrapper.get() != null) {
			return valueWrapper.get();
		}
		return null;
	}

	/**
	 * 获取SYS_CACHE缓存
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object get(String key, Object defaultValue) {
		Object value = get(key);
		return value != null ? value : defaultValue;
	}

	/**
	 * 写入SYS_CACHE缓存
	 * 
	 * @param key
	 * @return
	 */
	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}

	/**
	 * 从SYS_CACHE缓存中移除
	 * 
	 * @param key
	 * @return
	 */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}

	/**
	 * 获取缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static ValueWrapper get(String cacheName, String key) {
		return getCache(cacheName).get(getKey(key));
	}

	/**
	 * 获取缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object get(String cacheName, String key, Object defaultValue) {
		Object value = get(cacheName, getKey(key));
		return value != null ? value : defaultValue;
	}

	/**
	 * 写入缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		getCache(cacheName).put(getKey(key), value);
	}

	/**
	 * 从缓存中移除
	 * 
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).evict(key);
	}

	/**
	 * 从缓存中移除所有
	 * 
	 * @param cacheName
	 */
	public static void removeAll(String cacheName) {
		getCache(cacheName).clear();
	}

	/**
	 * 获取缓存键名，多数据源下增加数据源名称前缀
	 * 
	 * @param key
	 * @return
	 */
	private static String getKey(String key) {
		return key;
	}

	/**
	 * 获得一个Cache，没有则显示日志。
	 * 
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			throw new RuntimeException("当前系统中没有定义“" + cacheName + "”这个缓存。");
		}
		return cache;
	}

}
