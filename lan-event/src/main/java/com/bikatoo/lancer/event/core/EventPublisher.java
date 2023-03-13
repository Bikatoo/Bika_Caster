package com.bikatoo.lancer.event.core;

public interface EventPublisher<E extends Event> {

    void publish(E event);
}
