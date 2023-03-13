package com.bikatoo.lancer.event.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bikatoo.lancer.auth.core.PrincipalType;
import com.bikatoo.lancer.common.model.BaseDO;
import com.bikatoo.lancer.event.core.EventType;
import java.util.Date;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "e_event_trigger_record")
public class EventTriggerRecord extends BaseDO<EventTriggerRecord> {

  @TableId(value = "event_trigger_record_id", type = IdType.AUTO)
  private Long eventTriggerRecordId;

  private EventType type;

  private UUID eventId;

  // 触发者id
  private Long operatorId;

  // 触发者类型
  private PrincipalType operatorType;

  private Date triggerAt;

  private String snapshot;
}
