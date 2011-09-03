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

package ru.metrika4j.impl;

import ru.metrika4j.ReportItem;
import ru.metrika4j.json.JsonObject;

/** @author asuilin */
public class ReportItemImpl implements ReportItem {

    private final JsonObject source;

    public ReportItemImpl(JsonObject source) {
        this.source = source;
    }

    public Integer getInt(String field) {
        return source.getIntField(field);
    }

    public String getString(String field) {
        return source.getStringField(field);
    }

    public Double getDouble(String field) {
        return source.getDoubleField(field);
    }

    public ReportItem getRecord(String field) {
        JsonObject valueNode = source.getObjectField(field);
        return valueNode == null ? null : new ReportItemImpl(valueNode);
    }

    public ReportItem[] getArray(String field) {
        JsonObject[] valueNode = source.getObjectArray(field);
        ReportItem[] result = null;
        if (valueNode != null) {
            result = new ReportItem[valueNode.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = new ReportItemImpl(valueNode[i]);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return source.toString();
    }

}
