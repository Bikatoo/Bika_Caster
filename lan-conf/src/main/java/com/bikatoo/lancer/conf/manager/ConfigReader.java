package com.bikatoo.lancer.conf.manager;

import com.bikatoo.lancer.cache.core.config.ConfigCache;
import com.bikatoo.lancer.common.singleton.SingletonObjectMapper;
import com.bikatoo.lancer.conf.mapper.ConfigMapper;
import com.bikatoo.lancer.conf.model.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfigReader {

  @Resource
  private ConfigMapper configMapper;

  @Resource
  private ConfigCache configCache;

  public String readConfig(String configKey) {

    String value = configCache.readConfig(configKey);

    if (value == null) {
      Config config = configMapper.selectOne(w -> {
        w.eq(Config::getConfKey, configKey);
        w.isNull(Config::getDeleteTime);
      });
      if (config != null) {
        value = config.getConfValue();
        configCache.cacheConfig(configKey, value);
      }
    }
    return value;
  }

  public <T> T readConfig(String configKey, TypeReference<T> reference) {
    String value = readConfig(configKey);
    if (value == null) {
      return null;
    }
    ObjectMapper om = SingletonObjectMapper.OM.getOm();
    try {
      return om.readValue(value, reference);
    } catch (JsonProcessingException e) {
      log.error("readConfig json解析失败 [{}]", configKey);
      log.error("exception", e);
    }
    return null;
  }
}
