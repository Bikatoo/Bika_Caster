package com.bikatoo.lancer.common.singleton;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public enum SingletonObjectMapper {

  OM;

  @Getter
  private final ObjectMapper om;

  SingletonObjectMapper() {
    ObjectMapper om = new ObjectMapper();
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    om.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
    this.om = om;
  }


}
