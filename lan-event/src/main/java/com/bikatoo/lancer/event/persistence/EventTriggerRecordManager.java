package com.bikatoo.lancer.event.persistence;

import com.bikatoo.lancer.auth.core.Principal;
import com.bikatoo.lancer.event.core.Event;
import com.bikatoo.lancer.event.persistence.mapper.EventTriggerRecordMapper;
import com.bikatoo.lancer.event.persistence.model.EventTriggerRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventTriggerRecordManager {

  @Resource
  private EventTriggerRecordMapper eventTriggerRecordMapper;

  public void addRecord(Event event) {

    EventTriggerRecord record = new EventTriggerRecord();
    record.setEventId(event.getEventId());
    record.setType(event.getType());
    record.setTriggerAt(event.getTriggerAt());

    Principal op = event.getOperator();
    if (op != null) {
      record.setOperatorId(op.getId());
      record.setOperatorType(op.getType());
    }
    try {
      String snapshot = new ObjectMapper().writeValueAsString(event);
      record.setSnapshot(snapshot);
      log.info("add event record [{}]", snapshot);
    } catch (JsonProcessingException e) {
      log.error("event write fail", e);
    }
    eventTriggerRecordMapper.insert(record);
  }

}
