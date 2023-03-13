package com.bikatoo.lancer.cache.core.account;

import com.bikatoo.lancer.cache.expire.ExpireParam;
import com.bikatoo.lancer.cache.core.base.Cache;
import com.bikatoo.lancer.cache.redis.RedisKey;
import com.bikatoo.lancer.cache.redis.RedisManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountCache implements Cache {

    @Resource
    private RedisManager redisManager;

    /**
     * 缓存账号token -> accountId
     * 保持登录状态
     */
    public void cacheToken2accountId(String token, Long accountId) {
        try {
            redisManager.set(String.format(getKey(), token), String.valueOf(accountId), ExpireParam.ONE_DAY);
        } catch (JsonProcessingException e) {
            log.error("accountIdToken2IdCache fail [{}] [{}]", token, accountId);
            log.error("exception", e);
        }
    }

    /**
     * 根据token 获取 userId
     * 可用以判断登录状态
     */
    public Long getAccountIdByToken(String token) {
        String accountIdStr = redisManager.get(String.format(getKey(), token));
        if (StringUtils.isBlank(accountIdStr)) {
            return null;
        }
        return Long.parseLong(accountIdStr);
    }

    public void remove(String token) {
        redisManager.removeByKey(String.format(getKey(), token));
    }

    @Override
    public String getName() {
        return "账号token信息";
    }

    @Override
    public String getKey() {
        return RedisKey.ACCOUNT_TOKEN_ID_CACHE.getKey();
    }

    @Override
    public String getRedisKeyPrefix() {
        return RedisKey.ACCOUNT_TOKEN_ID_CACHE.getPrefix();
    }
}
