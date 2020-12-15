/*
 * Copyright (c) 2020 掘艺网络(jueyi.co).
 * All rights reserved.
 */

package co.jueyi.geekshop.custom.mybatis_plus;

import co.jueyi.geekshop.types.common.ConfigArg;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.IOException;
import java.util.List;

/**
 * Created on Dec, 2020 by @author bobo
 */
@Slf4j
@MappedTypes({List.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ConfigArgListTypeHandler extends AbstractJsonTypeHandler<List<ConfigArg>> {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected List<ConfigArg> parse(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<ConfigArg>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String toJson(List<ConfigArg> obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
