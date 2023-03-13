package com.bikatoo.lancer.cache.expire;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public enum ExpireParam {

    ONE_DAY(1L, TimeUnit.DAYS),

    ONE_HOUR(1L, TimeUnit.HOURS),

    FIVE_MINUTES(5L, TimeUnit.MINUTES)




    ;

    private final Long num;

    private final TimeUnit timeUnit;


    ExpireParam(Long num, TimeUnit timeUnit) {
        this.num = num;
        this.timeUnit = timeUnit;
    }

}
