package com.bikatoo.lancer.event.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AsyncEventExecutors {

    @Bean
    ThreadPoolExecutor executor() {
        return new ThreadPoolExecutor(2, 2, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    }

}
