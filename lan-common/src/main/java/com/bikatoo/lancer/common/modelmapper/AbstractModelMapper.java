package com.bikatoo.lancer.common.modelmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;

/** 字段名 match 策略默认 STRICT */
public abstract class AbstractModelMapper {

  private final ModelMapper MM = new ModelMapper();

  public AbstractModelMapper() {
    this(null, null);
  }

  public AbstractModelMapper(Consumer<ModelMapper> customize) {
    this(null, customize);
  }

  public AbstractModelMapper(ModelMapperHelper helper) {
    this(helper, null);
  }

  public AbstractModelMapper(
      @Nullable ModelMapperHelper helper, @Nullable Consumer<ModelMapper> customize) {
    if (helper == null) {
      helper = ModelMapperHelper.DEFAULT;
    }

    if (customize != null) {
      customize.accept(MM);
    }

    MM.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    // JSON 和 String 的转换
    configureJsonStringMappers(MM);

    configure(MM);

    configure(MM, helper);
  }

  public <S, T> Function<S, T> mapTo(Class<T> clazz) {
    return v -> MM.map(v, clazz);
  }

  public <S, T> T map(S s, Class<T> clazz) {
    if (s == null) {
      return null;
    }
    return MM.map(s, clazz);
  }

  public <S, T> PageInfo<T> mapForPage(PageInfo<S> sPage, Class<T> clazz) {
    if (sPage == null) {
      return null;
    }
    List<S> entityList = sPage.getList();
    List<T> dtoList = entityList.stream().map(v -> map(v, clazz)).collect(Collectors.toList());
    PageInfo<T> pageInfo = new PageInfo<>();
    BeanUtils.copyProperties(sPage, pageInfo);
    pageInfo.setList(dtoList);
    return pageInfo;
  }

  /**
   * 配置模型映射
   *
   * @param helper 默认状态下的 ModelMapper，用于实现各类转换器
   */
  protected void configure(ModelMapper m, ModelMapperHelper helper) {}

  /** 配置模型映射 */
  protected void configure(ModelMapper m) {}

  private void configureJsonStringMappers(ModelMapper m) {
    MM.addConverter(
        context -> {
          String source = context.getSource();
          return source == null ? null : JsonTools.JSON.fromString(source, ObjectNode.class);
        },
        String.class,
        ObjectNode.class);

    MM.addConverter(
        context -> {
          String source = context.getSource();
          return source == null ? null : JsonTools.JSON.fromString(source, ArrayNode.class);
        },
        String.class,
        ArrayNode.class);

    MM.addConverter(
        context -> {
          String source = context.getSource();
          return source == null ? null : JsonTools.JSON.fromString(source);
        },
        String.class,
        JsonNode.class);

    MM.addConverter(
        context -> {
          JsonNode source = context.getSource();
          return source == null ? null : JsonTools.JSON.toString(source);
        },
        JsonNode.class,
        String.class);
  }

  public static class ModelMapperHelper {

    public static final ModelMapperHelper DEFAULT = new ModelMapperHelper();

    private final ModelMapper m;

    private ModelMapperHelper() {
      this.m = new ModelMapper();
      this.m.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ModelMapperHelper(Consumer<ModelMapper> customizer) {
      this.m = new ModelMapper();
      this.m.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
      customizer.accept(m);
    }

    public <S, T> Function<S, T> mapTo(Class<T> clazz) {
      return v -> m.map(v, clazz);
    }

    public <S, T> T map(S s, Class<T> clazz) {
      return m.map(s, clazz);
    }
  }
}
