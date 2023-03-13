package com.bikatoo.lancer.cache.service;

import com.bikatoo.lancer.cache.model.CacheData;
import java.util.List;

public interface CacheService {

  List<CacheData> queryCacheData(String keyword);

  void clean(String key);

}
