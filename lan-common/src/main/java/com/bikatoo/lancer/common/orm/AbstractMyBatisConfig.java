package com.bikatoo.lancer.common.orm;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.UUID;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.context.annotation.Bean;

public abstract class AbstractMyBatisConfig {

    @Bean
    public TypeHandlerRegistry typeHandlers(SqlSessionFactory sqlSessionFactory) {
        TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        typeHandlerRegistry.register(UUID.class, JdbcType.VARCHAR, new StringUUIDTypeHandler());
        typeHandlerRegistry.register(JsonNode.class, JacksonTypeHandler.class);
        typeHandlerRegistry.register(ObjectNode.class, JacksonTypeHandler.class);
        typeHandlerRegistry.register(ArrayNode.class, JacksonTypeHandler.class);
        return typeHandlerRegistry;
    }

}
