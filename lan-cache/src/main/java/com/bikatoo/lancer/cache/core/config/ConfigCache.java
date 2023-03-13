package com.bikatoo.lancer.cache.core.config;

import com.bikatoo.lancer.cache.core.base.Cache;
import com.bikatoo.lancer.cache.redis.RedisKey;
import com.bikatoo.lancer.cache.redis.RedisManager;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfigCache implements Cache {

    @Resource
    private RedisManager redisManager;

    public String readConfig(String configKey) {
        return redisManager.getInHash(getKey(), configKey);
    }

    public void cacheConfig(String configKey, String value) {
        redisManager.putInHash(getKey(), configKey, value);
    }

    public void cleanCache(String configKey) {
        if (configKey == null) {
            redisManager.cleanInHash(getKey());
            return;
        }
        redisManager.removeInHash(getKey(), configKey);
    }

    @Override
    public String getName() {
        return "配置信息";
    }

    @Override
    public String getKey() {
        return RedisKey.CONFIG_KV_HASH.getKey();
    }

    @Override
    public String getRedisKeyPrefix() {
        return RedisKey.CONFIG_KV_HASH.getPrefix();
    }
}
