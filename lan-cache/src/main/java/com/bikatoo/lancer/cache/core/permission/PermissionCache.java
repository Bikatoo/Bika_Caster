package com.bikatoo.lancer.cache.core.permission;

import com.bikatoo.lancer.cache.core.base.Cache;
import com.bikatoo.lancer.cache.expire.ExpireParam;
import com.bikatoo.lancer.cache.redis.RedisKey;
import com.bikatoo.lancer.cache.redis.RedisManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PermissionCache implements Cache {

    @Resource
    private RedisManager redisManager;

    public void cacheAccountId2Permissions(Long accountId, Set<String> permissions) {

        permissions = permissions == null ? Sets.newHashSet() : permissions;
        try {
            redisManager.set(String.format(getKey(), accountId), StringUtils.join(permissions, ','), ExpireParam.ONE_DAY);
        } catch (JsonProcessingException e) {
            log.error("cacheAccountId2Permissions fail [{}] [{}]", accountId, permissions);
            log.error("exception", e);
        }
    }

    public Set<String> getPermissionsByAccountId(Long accountId) {
        String permissionStr = redisManager.get(String.format(getKey(), accountId));
        if (permissionStr == null) {
            return Sets.newHashSet();
        }
        return Arrays.stream(permissionStr.split(",")).filter(StringUtils::isNoneBlank).collect(Collectors.toSet());
    }

    @Override
    public String getName() {
        return "账号权限缓存";
    }

    @Override
    public String getKey() {
        return RedisKey.ACCOUNT_PERMISSION_CACHE.getKey();
    }

    @Override
    public String getRedisKeyPrefix() {
        return RedisKey.ACCOUNT_PERMISSION_CACHE.getPrefix();
    }
}
