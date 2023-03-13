package com.bikatoo.lancer.common.json;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

public class CustomJsonSerializer {
    private final ObjectMapper mapper = new ObjectMapper();
    private final CustomFilterProvider customFilterProvider = new CustomFilterProvider();

    @JsonFilter("dynamicJsonFilter")
    private interface DynamicJsonFilter {
    }

    /**
     * @param clazz   target type
     * @param include include fields
     * @param filter  filter fields
     */
    public void filter(Class<?> clazz, String include, String filter) {
        if (clazz == null) {
            return;
        }
        if (StringUtils.isNotBlank(include)) {
            customFilterProvider.include(clazz, include.split(","));
        }
        if (StringUtils.isNotBlank(filter)) {
            customFilterProvider.filter(clazz, filter.split(","));
        }
        mapper.addMixIn(clazz, DynamicJsonFilter.class);
    }

    public String toJson(Object object) throws JsonProcessingException {
        mapper.setFilterProvider(customFilterProvider);
        return mapper.writeValueAsString(object);
    }

    public void filter(JSON json) {
        this.filter(json.type(), json.include(), json.filter());
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
