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

import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;

import java.util.regex.Pattern;

/**
 * Транслирует имена из Java naming convention (Camel Case) в С naming convention (underscores)
 *
 * @author Artur Suilin
 */
public class MetrikaNamingStrategy extends PropertyNamingStrategy {

    // see http://stackoverflow.com/questions/2559759/how-do-i-convert-camelcase-into-human-readable-names-in-java
    private static final Pattern CAMEL_CASE_SPLIT = Pattern.compile(
            String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
                    "(?<=[A-Za-z])(?=[^A-Za-z])"));

    private static String convertCamelCase(String s) {
        String[] words = CAMEL_CASE_SPLIT.split(s);
        if (words.length == 0) {
            return s;
        } else {
            StringBuilder result = new StringBuilder(s.length() + words.length);
            for (String word : words) {
                result.append(word.toLowerCase()).append('_');
            }
            return result.substring(0, result.length() - 1);
        }
    }


    @Override
    public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
        return convertCamelCase(defaultName);
    }

    @Override
    public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        return convertCamelCase(defaultName);
    }

    @Override
    public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        return convertCamelCase(defaultName);
    }
}
