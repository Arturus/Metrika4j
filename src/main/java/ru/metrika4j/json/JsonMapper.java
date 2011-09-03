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
 * Обработчик JSON: парсинг, сериализация/десериализация Java объектов
 *
 * @author Artur Suilin
 */
public interface JsonMapper {

    /**
     * Создает Java объект на основе JSON-объекта.
     *
     * @param jsonObject входящий JSON объект
     * @param clazz      класс Java объекта
     * @param <T>        Типизация (должна соответствовать классу)
     * @throws ru.metrika4j.error.ParseException
     *
     */
    <T> T jsonObjectToEntity(JsonObject jsonObject, Class<T> clazz);

    /**
     * Сериализует Java объект в строковое JSON представление
     *
     * @throws ru.metrika4j.error.JsonSerializationException
     *
     */
    String entityToString(Object entity);

    /**
     * Парсит JSON строку и создает на её основе Json-объект. Если исходная строка не содержит валидного
     * JSON-объекта, бросает {@link ru.metrika4j.error.ParseException}
     *
     * @throws ru.metrika4j.error.ParseException
     *
     */
    JsonObject stringToJsonObject(String source);

}
