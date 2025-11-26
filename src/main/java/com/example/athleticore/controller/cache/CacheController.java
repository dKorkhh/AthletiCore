package com.example.athleticore.controller.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache")
@RequiredArgsConstructor
public class CacheController {
    private final CacheManager cacheManager;

    @DeleteMapping("/clear/{cacheName}")
    public String clearCache(@PathVariable String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
            return "Cache '" + cacheName + "' cleared!";
        }
        return "Cache '" + cacheName + "' not found!";
    }

    @DeleteMapping("/clear-all")
    public String clearAllCaches() {
        for (String name : cacheManager.getCacheNames()) {
            cacheManager.getCache(name).clear();
        }
        return "All caches cleared!";
    }
}
