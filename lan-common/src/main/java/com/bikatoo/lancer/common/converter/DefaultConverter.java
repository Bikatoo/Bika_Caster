package com.bikatoo.lancer.common.converter;

import com.bikatoo.lancer.common.exception.GlobalException;
import com.bikatoo.lancer.common.utils.PreconditionUtil;
import java.lang.reflect.InvocationTargetException;
import org.springframework.beans.BeanUtils;

public abstract class DefaultConverter<R, P> implements Converter<R, P> {

  @Override
  public P reqToParams(R req, Class<P> clazz) {

    if (req == null) {
      return null;
    }
    PreconditionUtil.checkNonNullAndThrow(clazz, new GlobalException("clazz is null"));

    P p = null;
    try {
      p = clazz.getDeclaredConstructor().newInstance();
      BeanUtils.copyProperties(req, p);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    return p;
  }
}
