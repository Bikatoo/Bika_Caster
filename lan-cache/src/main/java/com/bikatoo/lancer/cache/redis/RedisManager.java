package com.bikatoo.lancer.cache.redis;

import com.bikatoo.lancer.cache.expire.ExpireParam;
import com.bikatoo.lancer.common.exception.GlobalException;
import com.bikatoo.lancer.common.singleton.SingletonObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class RedisManager {

  @Resource
  private RedisTemplate<String, String> template;

  public void set(String key, String value) throws JsonProcessingException {
    set(key, value, null);
  }

  public void set(String key, String value, ExpireParam expire) throws JsonProcessingException {
    if (expire == null) {
      template.opsForValue().set(key, value);
      return;
    }
    template.opsForValue().set(key, value, expire.getNum(), expire.getTimeUnit());
  }

  public String get(String key) {
    return template.opsForValue().get(key);
  }

  public <T> T get(String key, TypeReference<T> reference) throws JsonProcessingException {
    String valueStr = get(key);

    if (StringUtils.isBlank(valueStr)) {
      return null;
    }
    ObjectMapper om = SingletonObjectMapper.OM.getOm();
    return om.readValue(valueStr, reference);
  }

  public void increment(String key) {
    template.opsForValue().increment(key);
  }

  public void decrement(String key) {
    template.opsForValue().decrement(key);
  }

  public <T> T getInHash(String hashKey, String key, TypeReference<T> reference)
      throws JsonProcessingException {
    String valueStr = getInHash(hashKey, key);

    if (StringUtils.isBlank(valueStr)) {
      return null;
    }
    ObjectMapper om = SingletonObjectMapper.OM.getOm();
    return om.readValue(valueStr, reference);
  }

  public String getInHash(String hashKey, String key) {
    return (String) template.opsForHash().get(hashKey, key);
  }

  public void putInHash(String hashKey, String key, String value) {
    template.opsForHash().put(hashKey, key, value);
  }

  public void removeInHash(String hashKey, String key) {
    template.opsForHash().delete(hashKey, key);
  }

  public void cleanInHash(String hashKey) {
    HashOperations<String, Object, Object> ho = template.opsForHash();
    Set<Object> keys = ho.keys(hashKey);
    keys.forEach(key -> ho.delete(hashKey, key));
  }

  public void removeByPrefix(String prefix) {

    if (StringUtils.isBlank(prefix)) {
      throw new GlobalException("前缀不合法");
    }
    Set<String> keys = template.keys(prefix + "*");
    if (keys == null) {
      return;
    }
    keys.forEach(key -> {
      Boolean res = template.delete(key);
      log.info("缓存清除 [{}] [{}]", key, res);
    });
  }

  public void removeByKey(String key) {

    if (StringUtils.isBlank(key)) {
      throw new GlobalException("key不合法");
    }
    template.delete(key);
  }

  public void addInBitMap(String key, long offset, boolean v) {
    template.opsForValue().setBit(key, offset, v);
  }

  public Boolean getInBitMap(String key, long offset) {
    return template.opsForValue().getBit(key, offset);
  }


}
