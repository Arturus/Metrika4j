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

package ru.metrika4j.json.org;

import org.json.JSONException;
import org.json.JSONObject;
import ru.metrika4j.entity.Counter;
import ru.metrika4j.entity.MetrikaError;
import ru.metrika4j.error.ParseException;
import ru.metrika4j.json.JsonMapper;
import ru.metrika4j.json.JsonObject;

/**
 * Облегченный JSON Mapper, годный для мобильных приложений под Android. Использует библиотеку org.json, встроенную в
 * Android API - т.е. не требуется никаких дополнительных библиотек JSON парсеров. <br/>
 * Облегченность заключается в следующем:
 * <ul>
 * <li>Не поддерживается сериализация сущностей в JSON</li>
 * <li>Десериализация поддерживается только для сущности {@link Counter} </li>
 * <li>Внутри Counter десериализуются только id, сайт и название счетчика </li>
 * <li>Функционал, относящийся к отчетам, полностью работоспособен </li>
 * </ul>
 *
 * @author Artur Suilin
 */
public class OrgJsonMapper implements JsonMapper {

    private Counter parseCounter(JsonObject jsonObject) {
        Counter result = new Counter();
        result.setId(jsonObject.getIntField("id"));
        result.setSite(jsonObject.getStringField("site"));
        result.setName(jsonObject.getStringField("name"));
        return result;
    }

    private MetrikaError parseError(JsonObject jsonObject) {
        return new MetrikaError(jsonObject.getIntField("id"), jsonObject.getStringField("text"),
                jsonObject.getStringField("code"));
    }

    public <T> T jsonObjectToEntity(JsonObject jsonObject, Class<T> clazz) {
        if (Counter.class.equals(clazz)) {
            //noinspection unchecked
            return (T) parseCounter(jsonObject);
        } else if (MetrikaError.class.equals(clazz)) {
            //noinspection unchecked
            return (T) parseError(jsonObject);
        } else {
            throw new UnsupportedOperationException("Entity class " + clazz.getName() + " is not supported");
        }
    }

    public String entityToString(Object entity) {
        throw new UnsupportedOperationException();
    }

    public JsonObject stringToJsonObject(String source) {
        try {
            return new OrgJsonObject(new JSONObject(source));
        } catch (JSONException e) {
            throw new ParseException(source, e);
        }
    }
}
