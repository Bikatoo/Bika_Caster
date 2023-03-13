package com.bikatoo.lancer.cache.core.base;

public interface Cache {

  String getName();

  String getKey();

  String getRedisKeyPrefix();

}
