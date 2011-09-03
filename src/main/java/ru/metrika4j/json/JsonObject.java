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

package ru.metrika4j.json;


/**
 * Внутреннее представление JSON объекта
 *
 * @author Artur Suilin
 */
public interface JsonObject {
    /**
     * Возвращает целочисленное значение поля в JSON объекте или null, если такого поля не существует, или
     * значение поля невозможно привести к типу Integer
     */
    Integer getIntField(String fieldName);

    /**
     * Возвращает строковое значение поля в JSON объекте или null, если такого поля не существует, или
     * значение поля невозможно привести к типу String
     */
    String getStringField(String fieldName);

    /**
     * Возвращает значение поля в JSON объекте или null, если такого поля не существует, или
     * значение поля невозможно привести к типу Double
     */
    Double getDoubleField(String fieldName);

    /**
     * Возвращает значение поля в JSON объекте, тоже являющееся JSON объектом, или null,
     * если такого поля не существует, или
     * значение поля не является JSON объектом
     */
    JsonObject getObjectField(String fieldName);

    /**
     * Возвращает значение поля в JSON объекте, являющееся массивом JSON объектов, или null,
     * если такого поля не существует, или
     * значение поля не является массивом JSON объектов
     */
    JsonObject[] getObjectArray(String fieldName);

    /** Возвращает true если в JSON объекте есть поле с заданным именем */
    boolean hasField(String fieldName);


}
