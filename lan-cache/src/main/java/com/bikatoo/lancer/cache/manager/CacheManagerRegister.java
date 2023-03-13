package com.bikatoo.lancer.cache.manager;

import com.bikatoo.lancer.cache.core.base.Cache;
import com.google.common.collect.Maps;
import java.util.Map;

public class CacheManagerRegister {

  private final static Map<String, Cache> caches = Maps.newConcurrentMap();

  public CacheManagerRegister regist(Cache cache) {
    caches.put(cache.getKey(), cache);
    return this;
  }

  public Map<String, Cache> getCaches() {
    return caches;
  }

}
