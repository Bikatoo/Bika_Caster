package com.bikatoo.lancer.cache.service.impl;

import com.bikatoo.lancer.cache.core.base.Cache;
import com.bikatoo.lancer.cache.manager.CacheManager;
import com.bikatoo.lancer.cache.model.CacheData;
import com.bikatoo.lancer.cache.service.CacheService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

  @Resource
  private CacheManager cacheManager;

  @Override
  public List<CacheData> queryCacheData(String keyword) {

    List<Cache> registeredCaches = cacheManager.getRegisteredCaches();
    return registeredCaches.stream()
        .filter(v -> keyword == null || v.getKey().contains(keyword) || v.getName().contains(keyword))
        .map(v -> {
          CacheData data = new CacheData();
          data.setKey(v.getKey());
          data.setName(v.getName());
          return data;
        })
        .collect(Collectors.toList());
  }

  @Override
  public void clean(String key) {
    cacheManager.cleanCache(key);
  }
}
