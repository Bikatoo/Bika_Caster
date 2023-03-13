package com.bikatoo.lancer.cache.core.user;

import com.bikatoo.lancer.cache.expire.ExpireParam;
import com.bikatoo.lancer.cache.core.base.Cache;
import com.bikatoo.lancer.cache.redis.RedisKey;
import com.bikatoo.lancer.cache.redis.RedisManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserLoginCaptchaCache implements Cache {

    @Resource
    private RedisManager redisManager;

  /**
     * 缓存用户验证码 phone -> captcha
     */
    public void cachePhoneNumber2CaptchaForLogin(String phoneNumber, String captcha) {
        try {
            redisManager.set(String.format(getKey(), phoneNumber), captcha, ExpireParam.FIVE_MINUTES);
        } catch (JsonProcessingException e) {
            log.error("cachePhoneNumber2CaptchaForLogin fail [{}] [{}]", phoneNumber, captcha);
            log.error("exception", e);
        }
    }

    /**
     * 根据电话获取验证码
     */
    public String getCaptchaByPhoneNumForLogin(String phoneNumber) {
        return redisManager.get(String.format(getKey(), phoneNumber));
    }

    @Override
    public String getName() {
        return "用户验证码";
    }

    @Override
    public String getKey() {
        return RedisKey.USER_LOGIN_PHONE_NUMBER_CAPTCHA_CACHE.getKey();
    }

    @Override
    public String getRedisKeyPrefix() {
        return RedisKey.USER_LOGIN_PHONE_NUMBER_CAPTCHA_CACHE.getPrefix();
    }
}
