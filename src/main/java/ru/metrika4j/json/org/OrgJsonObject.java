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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.metrika4j.error.ParseException;
import ru.metrika4j.json.JsonObject;

/** @author Artur Suilin */
public class OrgJsonObject implements JsonObject {
    private final JSONObject src;

    public OrgJsonObject(JSONObject src) {
        this.src = src;
    }


    public Integer getIntField(String fieldName) {
        try {
            return src.getInt(fieldName);
        } catch (JSONException e) {
            return null;
        }

    }

    public String getStringField(String fieldName) {
        try {
            return src.getString(fieldName);
        } catch (JSONException e) {
            return null;
        }
    }

    public Double getDoubleField(String fieldName) {
        try {
            return src.getDouble(fieldName);
        } catch (JSONException e) {
            return null;
        }
    }

    public JsonObject getObjectField(String fieldName) {
        try {
            return new OrgJsonObject(src.getJSONObject(fieldName));
        } catch (JSONException e) {
            return null;
        }
    }

    public JsonObject[] getObjectArray(String fieldName) {
        try {
            JSONArray array = src.getJSONArray(fieldName);
            JsonObject[] result = new JsonObject[array.length()];
            for (int i = 0; i < result.length; i++) {
                try {
                    result[i] = new OrgJsonObject(array.getJSONObject(i));
                } catch (JSONException e) {
                    throw new ParseException(src.toString(), e);
                }
            }
            return result;
        } catch (JSONException e) {
            return null;
        }
    }

    public boolean hasField(String fieldName) {
        return src.has(fieldName);
    }

    @Override
    public String toString() {
        return src.toString();
    }
}
