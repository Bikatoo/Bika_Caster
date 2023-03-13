package com.bikatoo.lancer.cache.config;

import com.bikatoo.lancer.cache.manager.CacheManagerRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

  @Bean
  public CacheManagerRegister cacheManagerRegister() {
    CacheManagerRegister register = new CacheManagerRegister();
    return register;
  }


}
