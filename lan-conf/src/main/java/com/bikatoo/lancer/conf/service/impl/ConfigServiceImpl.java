package com.bikatoo.lancer.conf.service.impl;

import static com.bikatoo.lancer.common.utils.PreconditionUtil.checkConditionAndThrow;
import static com.bikatoo.lancer.common.utils.PreconditionUtil.checkNonNullAndThrow;

import com.bikatoo.lancer.cache.core.config.ConfigCache;
import com.bikatoo.lancer.common.constants.Terminal;
import com.bikatoo.lancer.common.converter.DefaultDataObjectConverter;
import com.bikatoo.lancer.common.exception.GlobalException;
import com.bikatoo.lancer.common.model.PageParam;
import com.bikatoo.lancer.conf.mapper.ConfigMapper;
import com.bikatoo.lancer.conf.model.Config;
import com.bikatoo.lancer.conf.service.ConfigService;
import com.bikatoo.lancer.conf.service.serviceparams.ConfigCreate;
import com.bikatoo.lancer.conf.service.serviceparams.ConfigUpdate;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

  @Resource
  private ConfigMapper configMapper;

  @Resource
  private ConfigCache configCache;

  @Resource
  private DefaultDataObjectConverter converter;

  @Override
  public Long create(ConfigCreate create) {

    Config config = converter.map(create, Config.class);

    boolean exists = configMapper.exists(w -> {
      w.eq(Config::getConfKey, create.getConfKey());
      w.isNull(Config::getDeleteTime);
    });
    checkConditionAndThrow(!exists, new GlobalException("配置已存在，请在原配置中修改"));

    configMapper.insert(config);
    return config.getConfigId();
  }

  @Override
  public void delete(Long configId) {

    boolean exists = configMapper.exists(w -> {
      w.eq(Config::getConfigId, configId);
      w.isNull(Config::getDeleteTime);
    });
    checkConditionAndThrow(exists, new GlobalException("配置不存在"));
    configMapper.update(w -> {
      w.eq(Config::getConfigId, configId);
      w.isNull(Config::getDeleteTime);

      w.set(Config::getDeleteTime, new Date());
    });
  }

  @Override
  public void update(Long configId, ConfigUpdate update) {

    boolean exists = configMapper.exists(w -> {
      w.eq(Config::getConfigId, configId);
      w.isNull(Config::getDeleteTime);
    });
    checkConditionAndThrow(exists, new GlobalException("配置不存在"));

    boolean existsName = configMapper.exists(w -> {
      w.eq(Config::getConfKey, update.getConfKey());
      w.ne(Config::getConfigId, configId);
      w.isNull(Config::getDeleteTime);
    });

    checkConditionAndThrow(!existsName, new GlobalException("存在同名配置"));

    configMapper.update(w -> {
      w.eq(Config::getConfigId, configId);
      w.isNull(Config::getDeleteTime);
      if (StringUtils.isNotBlank(update.getConfKey())) {
        w.set(Config::getConfKey, update.getConfKey());
      }
      if (StringUtils.isNotBlank(update.getConfValue())) {
        w.set(Config::getConfValue, update.getConfValue());
      }
      if (update.getDescription() != null) {
        w.set(Config::getDescription, update.getDescription());
      }
      if (update.getTerminal() != null) {
        w.set(Config::getTerminal, update.getTerminal());
      }
      w.set(Config::getConfigId, configId);
    });
  }

  @Override
  public List<Config> queryConfig(String keyword, Terminal terminal, PageParam pageParam) {
    return configMapper.selectList(w -> {
      w.isNull(Config::getDeleteTime);
      if (terminal != null) {
        w.eq(Config::getTerminal, terminal);
      }
      if (StringUtils.isNotBlank(keyword)) {
        w.and(ww -> ww.like(Config::getDescription, keyword).or().like(Config::getConfKey, keyword));
      }
    });
  }

  @Override
  public PageInfo<Config> pageGetConfig(String keyword, Terminal terminal, PageParam pageParam) {

    checkNonNullAndThrow(pageParam, new GlobalException("参数错误"));

    return PageHelper.startPage(pageParam.getCurrentPage(), pageParam.getPageSize())
        .doSelectPageInfo(() -> queryConfig(keyword, terminal, pageParam));
  }

  @Override
  public void cleanCache(String confKey) {
    configCache.cleanCache(confKey);
  }

  @Override
  public Config queryById(Long configId) {
    return configMapper.selectOne(w -> {
      w.eq(Config::getConfigId, configId);
      w.isNull(Config::getDeleteTime);
    });
  }
}
