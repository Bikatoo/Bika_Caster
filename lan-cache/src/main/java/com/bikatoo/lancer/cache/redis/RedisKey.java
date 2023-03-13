package com.bikatoo.lancer.cache.redis;

import lombok.Getter;

public enum RedisKey {

    DEFAULT("default", "default"),

    // 文件 id -> url
    FILE_ID_URL_CACHE("file:id:url", "id:%s"),

    // 配置缓存 hash
    CONFIG_KV_HASH("config:kv:hash", null),

    // ip -> 位置信息
    IP_LOCATION_CACHE("thirdparty:ip:location", "ip:%s"),

    // 用户 token -> id
    USER_TOKEN_ID_CACHE("user:token:id", "token:%s"),

    // 账号id accountId -> permissions
    ACCOUNT_PERMISSION_CACHE("account:id:permission", "id:%s"),

    // 账号 token -> id
    ACCOUNT_TOKEN_ID_CACHE("account:token:id", "token:%s"),

    // 用户注册发送验证码 电话 -> 验证码
    USER_LOGIN_PHONE_NUMBER_CAPTCHA_CACHE("user:login:captcha", "phone:%s"),

    // 缓存形容词字典
    CON_ADJECTIVE("con:adjective", null),

    // uid 布隆过滤器位图
    USER_UID_BLOOM_FILTER("user:uid:bloom:filter", null),

    // 个创点赞数 creationId -> likeCount
    CREATION_LIKE_COUNT("creation:like:count", "creationId:%s"),

    // 个创收藏数 creationId -> collectCount
    CREATION_COLLECT_COUNT("creation:collect:count", "creationId:%s")

    ;

    @Getter
    private final String prefix;

    @Getter
    private final String param;

    RedisKey(String prefix, String param) {
        this.prefix = prefix;
        this.param = param;
    }

    public String getKey() {
        return param == null ? prefix : prefix + ':' + param;
    }
}
