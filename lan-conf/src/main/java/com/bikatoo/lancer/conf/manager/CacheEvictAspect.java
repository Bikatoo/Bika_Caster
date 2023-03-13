package com.bikatoo.lancer.conf.manager;

import com.bikatoo.lancer.cache.core.config.ConfigCache;
import com.bikatoo.lancer.conf.mapper.ConfigMapper;
import com.bikatoo.lancer.conf.model.Config;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class CacheEvictAspect {

    @Resource
    private ConfigCache configCache;
    @Resource
    private ConfigMapper configMapper;

    /**
     * 检验用户是否拥有实验读写权限
     */
    @Before("@annotation(configCacheEvict)")
    public void evict(JoinPoint joinPoint, ConfigCacheEvict configCacheEvict) {

        Object[] args = joinPoint.getArgs();
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();

        for (int i = 0 ; i < parameterNames.length ; i++) {
            if (parameterNames[i].equals("configId")) {
                Long configId = (Long) args[i];
                if (configId != null) {
                    Config config = configMapper.selectOne(w -> {
                        w.select(Config::getConfKey);
                        w.eq(Config::getConfigId, configId);
                        w.isNull(Config::getDeleteTime);
                    });
                    if (config != null && StringUtils.isNotBlank(config.getConfKey())) {
                        configCache.cleanCache(config.getConfKey());
                    }
                }
                break;
            }
        }
    }

}
