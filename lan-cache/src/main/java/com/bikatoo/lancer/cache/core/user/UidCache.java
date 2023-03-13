package com.bikatoo.lancer.cache.core.user;

import com.bikatoo.lancer.cache.core.base.Cache;
import com.bikatoo.lancer.cache.redis.RedisKey;
import com.bikatoo.lancer.cache.redis.RedisManager;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UidCache implements Cache {

    @Resource
    private RedisManager redisManager;

  /**
     * 缓存用户验证码 phone -> captcha
     */
    public void cacheUidBit(long offset, boolean value) {
      redisManager.addInBitMap(getKey(), offset, value);
    }

    /**
     * 根据电话获取验证码
     */
    public Boolean getUidBit(long offset) {
        return redisManager.getInBitMap(getKey(), offset);
    }

    @Override
    public String getName() {
        return "用户uid布隆过滤器";
    }

    @Override
    public String getKey() {
        return RedisKey.USER_UID_BLOOM_FILTER.getKey();
    }

    @Override
    public String getRedisKeyPrefix() {
        return RedisKey.USER_UID_BLOOM_FILTER.getPrefix();
    }
}
