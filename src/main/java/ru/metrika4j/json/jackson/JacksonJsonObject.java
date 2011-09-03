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

import org.codehaus.jackson.JsonNode;
import ru.metrika4j.json.JsonObject;

import java.util.Iterator;

/** @author Artur Suilin */
public class JacksonJsonObject implements JsonObject {
    private final JsonNode srcNode;

    public JsonNode getSrcNode() {
        return srcNode;
    }

    public JacksonJsonObject(org.codehaus.jackson.JsonNode srcNode) {
        this.srcNode = srcNode;
    }

    public Integer getIntField(String fieldName) {
        JsonNode valueNode = srcNode.get(fieldName);
        return valueNode == null ? null : valueNode.getValueAsInt();
    }

    public String getStringField(String fieldName) {
        JsonNode valueNode = srcNode.get(fieldName);
        return valueNode == null ? null : valueNode.getValueAsText();

    }

    public Double getDoubleField(String fieldName) {
        JsonNode valueNode = srcNode.get(fieldName);
        return valueNode == null ? null : valueNode.getValueAsDouble();

    }

    public JsonObject getObjectField(String fieldName) {
        JsonNode valueNode = srcNode.get(fieldName);
        return valueNode == null ? null : new JacksonJsonObject(valueNode);
    }

    public JsonObject[] getObjectArray(String fieldName) {
        JsonNode valueNode = srcNode.get(fieldName);
        if (valueNode == null) {
            return null;
        } else {
            JsonObject[] result = new JsonObject[valueNode.size()];
            Iterator<org.codehaus.jackson.JsonNode> it = valueNode.getElements();
            for (int i = 0; i < result.length; i++) {
                result[i] = new JacksonJsonObject(it.next());
            }
            return result;
        }
    }

    public boolean hasField(String fieldName) {
        return srcNode.has(fieldName);
    }

    @Override
    public String toString() {
        return srcNode.toString();
    }
}
