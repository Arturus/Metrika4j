/*
 * Copyright (C) 2011 Artur Suilin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.metrika4j.json.jackson;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import ru.metrika4j.error.JsonSerializationException;
import ru.metrika4j.error.ParseException;
import ru.metrika4j.json.JsonMapper;
import ru.metrika4j.json.JsonObject;

import java.io.IOException;

/**
 * Реализация JsonMapper, основанная на Jackson Java JSON-processor. Полностью поддерживается все функции JsonMapper.
 *
 * @author Artur Suilin
 */
public class JacksonMapper implements JsonMapper {
    private final ObjectMapper mapper;

    public JacksonMapper() {
        this.mapper = new ObjectMapper();
        SimpleModule testModule = new SimpleModule("MetrikaApiImpl", new Version(1, 0, 0, null));
        testModule.addDeserializer(Boolean.class, new MetrikaBooleanDeserializer(Boolean.class));
        testModule.addDeserializer(Boolean.TYPE, new MetrikaBooleanDeserializer(Boolean.TYPE));
        mapper.registerModule(testModule);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        mapper.configure(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.setPropertyNamingStrategy(new MetrikaNamingStrategy());

    }

    public <T> T jsonObjectToEntity(JsonObject jsonObject, Class<T> clazz) {
        try {
            return mapper.readValue(((JacksonJsonObject) jsonObject).getSrcNode(), clazz);
        } catch (IOException e) {
            throw new ParseException(jsonObject.toString(), e);
        }
    }


    public String entityToString(Object entity) {
        try {
            return mapper.writeValueAsString(entity);
        } catch (IOException e) {
            throw new JsonSerializationException(e);
        }
    }

    public JsonObject stringToJsonObject(String source) {
        try {
            return new JacksonJsonObject(mapper.readTree(source));
        } catch (IOException e) {
            throw new ParseException(source, e);
        }
    }
}
