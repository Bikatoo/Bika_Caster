package com.bikatoo.lancer.event.core;

import com.bikatoo.lancer.auth.core.Principal;
import java.util.Date;
import java.util.UUID;

public interface Event {

    Date getTriggerAt();

    UUID getEventId();

    EventType getType();

    Principal getOperator();
}
