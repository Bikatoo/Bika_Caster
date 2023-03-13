package com.bikatoo.lancer.conf.service.serviceparams;

import com.bikatoo.lancer.common.constants.Terminal;
import lombok.Data;

@Data
public class ConfigUpdate {

  private String confKey;

  private String confValue;

  private String description;

  private Terminal terminal;
}
