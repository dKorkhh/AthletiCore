package com.example.athleticore.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CustomCacheManager implements CacheManager {
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    @Override
    public Cache getCache(String name) {
        return caches.computeIfAbsent(name, CustomCache::new);
    }

    @Override
    public Collection<String> getCacheNames() {
        return caches.keySet();
    }
}
