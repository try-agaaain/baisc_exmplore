package com.example.redissondemo;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CacheService {

    @Autowired
    private RedissonClient redissonClient;

    public void setCacheValue(String key, String value) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(value, 10, TimeUnit.MINUTES);
    }

    public String getCacheValue(String key) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Scheduled(fixedRate = 6000) // 每60秒执行一次
    public void clearExpiredCaches() {
        log.info("Clear expired caches");
        RKeys keys = redissonClient.getKeys();
        // 使用模式匹配来删除一组特定的缓存键，例如以"temp:"开头的所有键
        Iterable<String> targetedKeys = keys.getKeysByPattern("*");
        for (String key : targetedKeys) {
            log.info("delete key: {}", key);
            keys.delete(key); // 删除匹配到的每个键
        }
    }
}