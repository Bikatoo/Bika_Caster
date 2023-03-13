package com.bikatoo.lancer.conf.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bikatoo.lancer.common.constants.Terminal;
import com.bikatoo.lancer.common.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "conf_config")
public class Config extends BaseDO<Config> {

  @TableId(value = "config_id", type = IdType.AUTO)
  private Long configId;

  private String confKey;

  private String confValue;

  private String description;

  private Terminal terminal;
}
