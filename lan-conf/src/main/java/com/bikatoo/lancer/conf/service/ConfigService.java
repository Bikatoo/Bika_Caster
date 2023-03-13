package com.bikatoo.lancer.conf.service;

import com.bikatoo.lancer.common.constants.Terminal;
import com.bikatoo.lancer.common.model.PageParam;
import com.bikatoo.lancer.conf.model.Config;
import com.bikatoo.lancer.conf.service.serviceparams.ConfigCreate;
import com.bikatoo.lancer.conf.service.serviceparams.ConfigUpdate;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface ConfigService {

  Long create(ConfigCreate create);

  void delete(Long configId);

  void update(Long configId, ConfigUpdate update);

  PageInfo<Config> pageGetConfig(String keyword, Terminal terminal, PageParam pageParam);

  List<Config> queryConfig(String keyword, Terminal terminal, PageParam pageParam);

  /**
   * remove config by key
   * if configKey is null => clean
   */
  void cleanCache(String confKey);

  Config queryById(Long configId);

}
