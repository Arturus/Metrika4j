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

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.TypeDeserializer;
import org.codehaus.jackson.map.deser.StdScalarDeserializer;

import java.io.IOException;

/**
 * Десериализатор, умеющий воспринимать строки "0" и "1" в качестве boolean значений
 *
 * @author Artur Suilin
 */
public class MetrikaBooleanDeserializer extends StdScalarDeserializer<Boolean> {
    public MetrikaBooleanDeserializer(Class<Boolean> cls) {
        super(cls);
    }

    protected final Boolean parseBoolean(JsonParser jp, DeserializationContext context) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if ("1".equals(text)) {
                return Boolean.TRUE;
            } else if ("0".equals(text) || text.length() == 0) {
                return Boolean.FALSE;
            }
        }
        return _parseBoolean(jp, context);
    }

    @Override
    public Boolean deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        return parseBoolean(jp, context);
    }

    // 1.6: since we can never have type info ("natural type"; String, Boolean, Integer, Double):
    // (is it an error to even call this version?)
    @Override
    public Boolean deserializeWithType(JsonParser jp, DeserializationContext context,
                                       TypeDeserializer typeDeserializer) throws IOException {
        return parseBoolean(jp, context);
    }


}
