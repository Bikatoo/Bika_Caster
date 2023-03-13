package com.bikatoo.lancer.event.core;

import com.bikatoo.lancer.auth.core.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public abstract class DomainEvent<H extends EventHandler> implements Event {

  private UUID eventId;

  private Date triggerAt;

  private Principal operator;

  public abstract void onEvent(List<H> handlers);

}
