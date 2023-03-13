package com.bikatoo.lancer.event.core;

import java.util.Comparator;

/**
 * 有优先级的
 * order 越小， 优先级越高
 */
public interface Priority {

    Integer getOrder();

    static Integer compare(Priority p1, Priority p2) {
        return Comparator.comparingInt(Priority::getOrder).compare(p1, p2);
    }
}
