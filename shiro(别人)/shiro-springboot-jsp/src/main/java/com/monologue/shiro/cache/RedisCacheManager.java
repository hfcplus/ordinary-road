package com.monologue.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * Created by Monologue_zsj on 2021/3/7 19:54
 * Author：小脸儿红扑扑
 * Description：自定义shiro缓存管理器
 */
public class RedisCacheManager implements CacheManager {
    /**
     * @param cacheName 认证或者授权缓存的统一名称
     * @param <K>
     * @param <V>
     * @return
     * @throws CacheException
     */
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {

        System.out.println(cacheName);

        return new RedisCache<K,V>(cacheName);
    }
}
