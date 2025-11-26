package com.example.athleticore.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
public class CustomCache implements Cache {
    private final String name;
    private final ConcurrentMap<Object, Object> map = new ConcurrentHashMap<>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return map;
    }

    @Override
    public ValueWrapper get(Object key) {
        return map.containsKey(key) ? () -> map.get(key) : null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        map.put(key, value);
    }

    @Override
    public void evict(Object key) {
        map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }
}
