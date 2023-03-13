package com.bikatoo.lancer.cache.manager;

import com.bikatoo.lancer.cache.core.base.Cache;
import com.bikatoo.lancer.cache.redis.RedisManager;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheManager {
  @Resource
  private CacheManagerRegister register;
  @Resource
  private RedisManager redisManager;

  public List<Cache> getRegisteredCaches() {
    Map<String, Cache> caches = register.getCaches();
    if (caches.isEmpty()) {
      return Lists.newArrayList();
    }

    return new ArrayList<>(caches.values());
  }

  public void cleanCache(String key) {
    Map<String, Cache> caches = register.getCaches();
    if (!caches.containsKey(key)) {
      log.error("缓存类型不存在 [{}]", key );
      return;
    }
    Cache cache = caches.get(key);
    redisManager.removeByPrefix(cache.getRedisKeyPrefix());
  }
}
