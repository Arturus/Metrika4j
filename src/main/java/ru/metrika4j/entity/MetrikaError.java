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

package ru.metrika4j.entity;


import org.codehaus.jackson.annotate.JsonProperty;

/** Ошибка, возвращаемая сервером API Метрики */
public class MetrikaError {
    @JsonProperty
    private String code;
    @JsonProperty
    private String text;
    @JsonProperty
    private Integer id;


    public MetrikaError(Integer id, String text, String code) {
        this.id = id;
        this.text = text;
        this.code = code;
    }

    /**
     * Код ошибки.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/error-codes.xml">список кодов</a>
     */
    public String getCode() {
        return code;
    }

    /** Текcтовое описание ошибки  (если есть) */
    public String getText() {
        return text;
    }

    public Integer getId() {
        return id;
    }


    public MetrikaError() {
    }


}
