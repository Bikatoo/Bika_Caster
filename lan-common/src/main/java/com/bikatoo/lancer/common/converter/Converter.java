package com.bikatoo.lancer.common.converter;

public interface Converter<R, P> {

    P reqToParams(R req);

    P reqToParams(R req, Class<P> clazz);
}
