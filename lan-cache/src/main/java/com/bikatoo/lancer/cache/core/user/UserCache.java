package com.bikatoo.lancer.cache.core.user;

import com.bikatoo.lancer.cache.expire.ExpireParam;
import com.bikatoo.lancer.cache.core.base.Cache;
import com.bikatoo.lancer.cache.redis.RedisKey;
import com.bikatoo.lancer.cache.redis.RedisManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class UserCache implements Cache {

    @Resource
    private RedisManager redisManager;

    /**
     * 缓存用户token -> userId
     * 保持登录状态
     */
    public void cacheToken2userId(String token, Long userId) {
        try {
            redisManager.set(String.format(getKey(), token), String.valueOf(userId), ExpireParam.ONE_DAY);
        } catch (JsonProcessingException e) {
            log.error("userToken2IdCache fail [{}] [{}]", token, userId);
            log.error("exception", e);
        }
    }

    /**
     * 根据token 获取 userId
     * 可用以判断登录状态
     */
    public Long getUserByToken(String token) {
        String userIdStr = redisManager.get(String.format(getKey(), token));
        if (StringUtils.isBlank(userIdStr)) {
            return null;
        }
        return Long.parseLong(userIdStr);
    }

    public void remove(String token) {
        redisManager.removeByKey(String.format(getKey(), token));
    }

    @Override
    public String getName() {
        return "用户token信息";
    }

    @Override
    public String getKey() {
        return RedisKey.USER_TOKEN_ID_CACHE.getKey();
    }

    @Override
    public String getRedisKeyPrefix() {
        return RedisKey.USER_TOKEN_ID_CACHE.getPrefix();
    }
}
